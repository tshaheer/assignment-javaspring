package com.assignment.dao.impl;

import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.assignment.dao.UserDao;
import com.assignment.domain.User;
import com.assignment.domain.UserRowMapper;

@Component
public class UserDaoImpl implements UserDao {

	private final JdbcTemplate jdbcTemplate;

	public UserDaoImpl(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Optional<User> findByUsername(String username) {
		String sql = "SELECT * FROM User where username=?";
		User user = jdbcTemplate.query(sql, new UserRowMapper(), username);
		return Optional.ofNullable(user);
	}

	@Override
	public void addToken(String username, String token) {
		String sql = "Update User SET token=?, session_active='true' where username=?";
		jdbcTemplate.update(sql, token, username);
	}

	@Override
	public void removeToken(String username) {
		String sql = "Update User SET token='', session_active='false' where username=?";
		jdbcTemplate.update(sql, username);
	}

}
