package com.cangzhitao.jbf.security.shiro;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

public class ShiroSessionListener implements SessionListener {
	
//	private static Logger logger = LoggerFactory.getLogger(ShiroSessionListener.class);

	private final AtomicInteger sessionCount = new AtomicInteger(0);
    
    public void onStart(Session session) {
        sessionCount.incrementAndGet();
    }

    public void onStop(Session session) {
        sessionCount.decrementAndGet();
    }

    public void onExpiration(Session session) {
        sessionCount.decrementAndGet();
        
    }

    public int getSessionCount() {
        return sessionCount.get();
    }

}
