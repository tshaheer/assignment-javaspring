package com.assignment.dao.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.assignment.dao.AccountDao;
import com.assignment.dao.StatementDao;
import com.assignment.domain.Account;
import com.assignment.domain.Statement;
import com.assignment.domain.StatementRowMapper;

@Component
public class StatementDaoImpl implements StatementDao {

	private final JdbcTemplate jdbcTemplate;
	
	private final AccountDao accountDao;

	public StatementDaoImpl(JdbcTemplate jdbcTemplate, AccountDao accountDao) {
		this.jdbcTemplate = jdbcTemplate;
		this.accountDao = accountDao;
	}

	@Override
	public List<Statement> findAllByAccountId(Long accountId) {
		Account account = accountDao.findById(accountId);
		if(account == null) {
			return Collections.emptyList();
		}
		String sql = "SELECT * FROM Statement where account_id=?";
		return jdbcTemplate.query(sql, new StatementRowMapper(account), accountId);
	}

}
