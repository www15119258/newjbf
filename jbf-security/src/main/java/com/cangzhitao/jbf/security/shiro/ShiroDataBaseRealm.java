package com.cangzhitao.jbf.security.shiro;

import java.util.Iterator;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.cangzhitao.jbf.security.entities.SysUser;
import com.cangzhitao.jbf.security.service.ISysUserService;
import com.cangzhitao.jbf.security.util.UserManager;


@Component
public class ShiroDataBaseRealm extends AuthorizingRealm {

	public static final String AUTHORIZATIONINFO_CACHE_NAME = "authorizationInfoCACHE";

	@Autowired
	@Lazy
	private ISysUserService sysUserService;
	
	@Autowired
	@Lazy
	@Qualifier("cacheManager")
	private CacheManager cacheManager;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String sessionId = UserManager.getSessionId();
		ValueWrapper cache = cacheManager.getCache(AUTHORIZATIONINFO_CACHE_NAME).get(sessionId);
		if(cache!=null) {
			SimpleAuthorizationInfo info = (SimpleAuthorizationInfo) cache.get();
			return info;
		}
		Set<String> perms = UserManager.getPermSet();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Iterator<String> it = perms.iterator();
		while(it.hasNext()) {
			info.addStringPermission(it.next());
		}
		cacheManager.getCache(AUTHORIZATIONINFO_CACHE_NAME).put(sessionId, info);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		String username = usernamePasswordToken.getUsername();
		if(username==null||"".equals(username)) {
			return null;
		}
		SysUser example = new SysUser().setNull().setProperty("username", username);
		SysUser user = sysUserService.findOne(example);
		if(user==null) {
			return null;
		}
		AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
		return info;
	}
	

}
