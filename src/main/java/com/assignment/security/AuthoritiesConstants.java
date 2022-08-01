package com.assignment.security;

/**
 * Constants for Spring Security authorities.
 * 
 * @author Shaheer
 * @since 1 August 2022
 */
public final class AuthoritiesConstants {

	public static final String ADMIN = "ROLE_ADMIN";

	public static final String USER = "ROLE_USER";

	public static final String ANONYMOUS = "ROLE_ANONYMOUS";

	private AuthoritiesConstants() {
	}
}