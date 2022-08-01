package com.assignment.security;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Authenticate a user from the database.
 * 
 * @author Shaheer
 * @since 1 August 2022
 */
@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	private final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String username) {
		log.debug("Authenticating {}", username);
		if ("user".equals(username)) {
			return createSpringSecurityUser(new User(username, passwordEncoder.encode("user"), List.of(AuthoritiesConstants.USER)));
		} else if ("admin".equals(username)) {
			return createSpringSecurityUser(new User(username, passwordEncoder.encode("admin"), List.of(AuthoritiesConstants.ADMIN)));
		}
		throw new UsernameNotFoundException("User " + username + " was not found in the database");
	}

	private org.springframework.security.core.userdetails.User createSpringSecurityUser(User user) {
		List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream().map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				grantedAuthorities);
	}

	/**
	 * Object to hold user info.
	 */
	class User {

		private final String username;

		private final String password;

		private final List<String> authorities;

		public User(String username, String password, List<String> authorities) {
			super();
			this.username = username;
			this.password = password;
			this.authorities = authorities;
		}

		public String getUsername() {
			return username;
		}

		public String getPassword() {
			return password;
		}

		public List<String> getAuthorities() {
			return authorities;
		}
	}
}
