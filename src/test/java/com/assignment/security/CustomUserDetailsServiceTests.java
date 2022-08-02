package com.assignment.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.assignment.dao.UserDao;
import com.assignment.domain.User;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTests {

	private static final String USER_USERNAME = "user";
	private static final String ADMIN_USERNAME = "admin";
	private static final String ANONYMOUS_USERNAME = "anonymous";

	@InjectMocks
	private CustomUserDetailsService customUserDetailsService;

	@Mock
	private UserDao userDao;

	private Optional<User> defaultUser;

	@BeforeEach
	void setUp() {
		defaultUser = Optional.of(new User(1L, ANONYMOUS_USERNAME, "user", "", false));
	}

	@Test
	void givenUsername_whenGetUser_thenReturnUser() {
		defaultUser.get().setUsername(USER_USERNAME);
		given(userDao.findByUsername(USER_USERNAME)).willReturn(defaultUser);
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(USER_USERNAME);
		assertThat(userDetails).isNotNull();
		assertThat(userDetails.getUsername()).isEqualTo(USER_USERNAME);
	}
	
	@Test
	void givenAdminUsername_whenGetUser_thenReturnUser() {
		defaultUser.get().setUsername(ADMIN_USERNAME);
		given(userDao.findByUsername(ADMIN_USERNAME)).willReturn(defaultUser);
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(ADMIN_USERNAME);
		assertThat(userDetails).isNotNull();
		assertThat(userDetails.getUsername()).isEqualTo(ADMIN_USERNAME);
	}
	
	@Test
	void givenNotExistUsername_whenGetUser_thenThrowUserNameNotFoundException() {
		UsernameNotFoundException usernameNotFoundException = assertThrows(UsernameNotFoundException.class,
				() -> customUserDetailsService.loadUserByUsername(ANONYMOUS_USERNAME));
		assertEquals("User anonymous was not found in the database", usernameNotFoundException.getMessage());
	}

	@Test
    void giventSessionExist_whenGetUser_thenThrowSessionExistException() {
		defaultUser.get().setSessionActive(true);
		defaultUser.get().setUsername(USER_USERNAME);
		given(userDao.findByUsername(USER_USERNAME)).willReturn(defaultUser);
		SessionExistException exception = assertThrows(SessionExistException.class,
				() -> customUserDetailsService.loadUserByUsername(USER_USERNAME));
		assertEquals("User user has active session. Please logout before authenticate.", exception.getMessage());
    }
}
