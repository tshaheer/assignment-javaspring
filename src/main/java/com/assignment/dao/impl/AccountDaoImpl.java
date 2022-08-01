package com.assignment.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.assignment.dao.AccountDao;
import com.assignment.domain.Account;
import com.assignment.domain.AccountRowMapper;

@Component
public class AccountDaoImpl implements AccountDao {

	private final JdbcTemplate jdbcTemplate;

	public AccountDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Account findById(Long id) {
		String sql = "SELECT * FROM ACCOUNT WHERE ID = ?";
		return jdbcTemplate.query(sql, new AccountRowMapper(), id);
	}

}
