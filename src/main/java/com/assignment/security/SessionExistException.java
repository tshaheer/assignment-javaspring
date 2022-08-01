package com.assignment.security;

import org.springframework.security.core.AuthenticationException;

/**
 * This exception is thrown in case of logged user trying to authenticate again.
 * 
 * @author Shaheer
 * @since 1 August 2022
 */
public class SessionExistException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public SessionExistException(String message) {
		super(message);
	}

	public SessionExistException(String message, Throwable t) {
		super(message, t);
	}
}
