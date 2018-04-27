package com.cangzhitao.jbf.security.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

public class ShiroSessionManager extends DefaultWebSessionManager {

//	private final static Logger log = LoggerFactory.getLogger(ShiroSessionManager.class);

	protected Session retrieveSession(SessionKey sessionKey) {
		try {
			return super.retrieveSession(sessionKey);
		} catch (UnknownSessionException e) {
			// 获取不到SESSION不抛出异常
			return null;
		}
	}

	public Session getSession(SessionKey key) {
		try {
			return super.getSession(key);
		} catch (Exception e) {
		}
		return null;
	}

	// protected void validate(Session session, SessionKey key) {
	// try {
	// doValidate(session);
	// } catch (ExpiredSessionException ese) {
	// onExpiration(session, ese, key);
	// } catch (InvalidSessionException ise) {
	// onInvalidation(session, ise, key);
	// }
	// }
	//
	// public Object getAttribute(SessionKey sessionKey, Object attributeKey) {
	// try {
	// return super.getAttribute(sessionKey, attributeKey);
	// } catch(Exception e) {
	// }
	// return null;
	// }

//	public void validateSessions() {
//		if (log.isInfoEnabled()) {
//			log.info("Validating all active sessions...");
//		}
//		int invalidCount = 0;
//		Collection<Session> activeSessions = getActiveSessions();
//		if (activeSessions != null && !activeSessions.isEmpty()) {
//			for (Session s : activeSessions) {
//				try {
//					SessionKey key = new DefaultSessionKey(s.getId());
//					validate(s, key);
//				} catch (InvalidSessionException e) {
//					if (log.isDebugEnabled()) {
//						boolean expired = (e instanceof ExpiredSessionException);
//						String msg = "Invalidated session with id [" + s.getId() + "]"
//								+ (expired ? " (expired)" : " (stopped)");
//						log.debug(msg);
//					}
//					invalidCount++;
//				}
//			}
//		}
//		if (log.isInfoEnabled()) {
//			String msg = "Finished session validation.";
//			if (invalidCount > 0) {
//				msg += "  [" + invalidCount + "] sessions were stopped.";
//			} else {
//				msg += "  No sessions were stopped.";
//			}
//			log.info(msg);
//		}
//	}
//
//	protected void afterExpired(Session session) {
//		super.afterExpired(session);
//    }
	
}
