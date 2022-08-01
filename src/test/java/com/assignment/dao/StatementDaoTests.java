package com.assignment.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.assignment.dao.impl.AccountDaoImpl;
import com.assignment.dao.impl.StatementDaoImpl;
import com.assignment.domain.Statement;

@JdbcTest
class StatementDaoTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private StatementDao statementDao;

	private AccountDao accountDao;

	@BeforeEach
	void setUp() {
		accountDao = new AccountDaoImpl(jdbcTemplate);
		statementDao = new StatementDaoImpl(jdbcTemplate, accountDao);
		jdbcTemplate.update(
				"INSERT INTO account(id, account_type, account_number) VALUES ('1', 'current', '0012250016004')");
		jdbcTemplate.update(
				"INSERT INTO statement(id, account_id, datefield, amount) VALUES ('1', '1', '15.11.2020', '967.410308637791')");
	}

	@Test
	void givenStatements_whenFindAll_thenStatementList() {
		List<Statement> statements = statementDao.findAllByAccountId(1L);
		assertThat(statements).hasSize(1);
	}

}
