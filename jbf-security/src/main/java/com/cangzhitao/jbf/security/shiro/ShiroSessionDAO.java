package com.cangzhitao.jbf.security.shiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ShiroSessionDAO extends AbstractSessionDAO {
	
	public static final String ACTIVE_SESSION_CACHE_NAME = "sys-shiro-activeSessionCache";
	public static final String ACTIVE_SESSION_ID_CACHE_NAME = "sys-shiro-activeSessionIdCache";
	public static final String ACTIVE_SESSION_ID = "activeSessionIde";
	public static final String AUTHORIZATIONINFO_CACHE_NAME = "authorizationInfoCACHE";
	
	
	@Autowired
	@Lazy
	@Qualifier("cacheManager")
	private CacheManager cacheManager;

	@Override
	public void update(Session session) throws UnknownSessionException {
		if(session==null) {
			throw new RuntimeException("session can not be null!");
		}
		if(session.getId()==null) {
			throw new RuntimeException("sessionId can not be null!");
		}
		cacheManager.getCache(ACTIVE_SESSION_CACHE_NAME).put(session.getId()+"", session);
		this.addSessionId(session.getId()+"");
	}

	@Override
	public void delete(Session session) {
		if(session==null) {
			throw new RuntimeException("session can not be null!");
		}
		if(session.getId()==null) {
			throw new RuntimeException("sessionId can not be null!");
		}
		this.delete(session.getId());
	}
	
	public void delete(Serializable sessionId) {
		if(sessionId==null) {
			throw new RuntimeException("sessionId can not be null!");
		}
		cacheManager.getCache(ACTIVE_SESSION_CACHE_NAME).evict(sessionId+"");
		this.removeSessionId(sessionId+"");
		cacheManager.getCache(AUTHORIZATIONINFO_CACHE_NAME).evict(sessionId+"");
	}

	@Override
	public Collection<Session> getActiveSessions() {
		Iterator<String> sessionIdIt = this.getActiveSessionIds();
		List<Session> sessions = new ArrayList<Session>();
		while(sessionIdIt.hasNext()) {
			String sessionId = sessionIdIt.next();
			Session session = null;
			try {
				session = (Session) cacheManager.getCache(ACTIVE_SESSION_CACHE_NAME).get(sessionId).get();
			} catch (Exception e) {
			}
			if(session!=null) {
				sessions.add(session);
			}
		}
		return sessions;
	}

	@Override
	protected Serializable doCreate(Session session) {
		if(session==null) {
			throw new RuntimeException("session can not be null!");
		}
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		cacheManager.getCache(ACTIVE_SESSION_CACHE_NAME).put(sessionId+"", session);
		this.addSessionId(sessionId+"");
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		if(sessionId==null) {
			throw new RuntimeException("sessionId can not be null!");
		}
		return (Session) cacheManager.getCache(ACTIVE_SESSION_CACHE_NAME).get(sessionId+"").get();
	}

	/**
	 * 判断用户是否已经登录
	 * @param usernameOrPk 用户名或者主键
	 * @return
	 */
	public Session isLogin(String usernameOrPk) {
		Collection<Session> sessions = this.getActiveSessions();
		for(Session session : sessions) {
			SimplePrincipalCollection principal = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)==null?null:(SimplePrincipalCollection)session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
			if(principal!=null) {
//				PlatformUserModel user = (PlatformUserModel) principal.getPrimaryPrincipal();
//				if(user.getPk_user().equals(usernameOrPk)||user.getUsername().equals(usernameOrPk)) {
//					return session;
//				}
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private void addSessionId(String id) {
		ValueWrapper cache = cacheManager.getCache(ACTIVE_SESSION_ID_CACHE_NAME).get(ACTIVE_SESSION_ID);
		ConcurrentMap<String, String> map = null;
		if(cache==null) {
			map = new ConcurrentHashMap<String, String>();
		} else {
			map = (ConcurrentMap<String, String>) cache.get();
		}
		map.put(id, "");
		cacheManager.getCache(ACTIVE_SESSION_ID_CACHE_NAME).put(ACTIVE_SESSION_ID, map);
	}
	
	@SuppressWarnings("unchecked")
	private void removeSessionId(String id) {
		ValueWrapper cache = cacheManager.getCache(ACTIVE_SESSION_ID_CACHE_NAME).get(ACTIVE_SESSION_ID);
		ConcurrentMap<String, String> map = null;
		if(cache==null) {
			map = new ConcurrentHashMap<String, String>();
		} else {
			map = (ConcurrentMap<String, String>) cache.get();
		}
		map.remove(id);
		cacheManager.getCache(ACTIVE_SESSION_ID_CACHE_NAME).put(ACTIVE_SESSION_ID, map);
	}
	
	@SuppressWarnings("unchecked")
	private Iterator<String> getActiveSessionIds() {
		ValueWrapper cache = cacheManager.getCache(ACTIVE_SESSION_ID_CACHE_NAME).get(ACTIVE_SESSION_ID);
		ConcurrentMap<String, String> map = null;
		if(cache==null) {
			map = new ConcurrentHashMap<String, String>();
		} else {
			map = (ConcurrentMap<String, String>) cache.get();
		}
		return map.keySet().iterator();
	}

}
