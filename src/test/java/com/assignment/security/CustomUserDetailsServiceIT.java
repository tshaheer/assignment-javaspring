package com.assignment.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CustomUserDetailsServiceIT {

	private static final String USER_USERNAME = "user";
	private static final String ADMIN_USERNAME = "admin";

	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService customUserDetailsService;

	@Test
	void givenUsername_whenGetUser_thenReturnUser() {
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(USER_USERNAME);
		assertThat(userDetails).isNotNull();
		assertThat(userDetails.getUsername()).isEqualTo(USER_USERNAME);
	}
	
	@Test
	void givenAdminUsername_whenGetUser_thenReturnUser() {
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(ADMIN_USERNAME);
		assertThat(userDetails).isNotNull();
		assertThat(userDetails.getUsername()).isEqualTo(ADMIN_USERNAME);
	}
	
	@Test
    void assertThatSessionExistExceptionIsThrownForMultipleLogin() {
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(ADMIN_USERNAME);
        assertThatExceptionOfType(SessionExistException.class)
            .isThrownBy(() -> customUserDetailsService.loadUserByUsername(ADMIN_USERNAME));
    }
}
