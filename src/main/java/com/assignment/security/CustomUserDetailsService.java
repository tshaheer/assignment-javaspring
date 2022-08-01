package com.assignment.security;

import java.util.ArrayList;
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
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.dao.UserDao;
import com.assignment.domain.User;

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
	private UserDao userDao;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String username) {
		log.debug("Authenticating {}", username);
		return userDao
	            .findByUsername(username)
	            .map(this::createSpringSecurityUser)
	            .orElseThrow(() -> new UsernameNotFoundException("User " + username + " was not found in the database"));
	}

	private org.springframework.security.core.userdetails.User createSpringSecurityUser(User user) {
		if(user.isSessionActive()) {
			throw new SessionExistException("User " + user.getUsername() + " has active session. Please logout before authenticate.");
		}
		List<String> roles = new ArrayList<>();
		if(user.getUsername().equals("admin")) {
			roles.add(AuthoritiesConstants.ADMIN);
		} else if(user.getUsername().equals("user")) {
			roles.add(AuthoritiesConstants.USER);
		}
		List<GrantedAuthority> grantedAuthorities = roles.stream().map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				grantedAuthorities);
	}
}
