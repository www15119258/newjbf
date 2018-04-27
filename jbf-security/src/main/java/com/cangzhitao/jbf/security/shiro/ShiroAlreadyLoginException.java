package com.cangzhitao.jbf.security.shiro;

import org.apache.shiro.authc.AuthenticationException;

public class ShiroAlreadyLoginException extends AuthenticationException {

	private static final long serialVersionUID = -4737795620988192160L;

	public ShiroAlreadyLoginException() {
		super();
	}

	public ShiroAlreadyLoginException(String message, Throwable cause) {
		super(message, cause);
	}

	public ShiroAlreadyLoginException(String message) {
		super(message);
	}

	public ShiroAlreadyLoginException(Throwable cause) {
		super(cause);
	}

}
