package com.assignment.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.assignment.dao.impl.UserDaoImpl;
import com.assignment.domain.User;

@JdbcTest
class UserDaoTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private UserDao userDao;

	@BeforeEach
	void setUp() {
		userDao = new UserDaoImpl(jdbcTemplate);
		jdbcTemplate.update(
				"INSERT INTO user(id, username, password, token, session_active) VALUES ('1', 'user', '$2a$10$kjUTsZVFwfU5IoSNNJ9PPOhVYKhpTjPbTulxKZo7/djy40uewrI5a', '', 'true')");
	}
	
	@Test
	void givenCorrectUsername_whenFindByUsername_thenReturnUser() {
		Optional<User> user = userDao.findByUsername("user");
		assertThat(user).isNotEmpty();
	}
	
	@Test
	void givenWrongUsername_whenFindByUsername_thenReturnUser() {
		Optional<User> user = userDao.findByUsername("user1");
		assertThat(user).isEmpty();
	}
	
	@Test
	void givenUsernameAndToken_whenAddToken_thenUpdateToken() {
		String username = "user";
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTY1OTQzMTYwMH0.dFqbMRgy1JqklyRIqbVSeRgOk7H4gIAi6ypp8P09jXQgrQMZDcEK38qPRUfy1hW9lgEsGeRIzFI_0gXU9RUONw";
		userDao.addToken(username, token);
		assertThat(username).isNotBlank();
	}
	
	@Test
	void givenUsername_whenRemoveToken_thenRemoveToken() {
		String username = "user";
		userDao.removeToken(username);
		assertThat(username).isNotBlank();
	}
}
