package com.assignment.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.assignment.dao.impl.AccountDaoImpl;
import com.assignment.domain.Account;

@JdbcTest
class AccountDaoTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private AccountDao accountDao;

	@BeforeEach
	void setUp() {
		accountDao = new AccountDaoImpl(jdbcTemplate);
		jdbcTemplate.update(
				"INSERT INTO account(id, account_type, account_number) VALUES ('1', 'current', '0012250016004')");
	}
	
	@Test
	void givenCorrectId_whenFindById_thenReturnAccount() {
		Account account = accountDao.findById(1L);
		assertThat(account).isNotNull();
	}
	
	@Test
	void givenWrongId_whenFindById_thenReturnNull() {
		Account account = accountDao.findById(Long.MAX_VALUE);
		assertThat(account).isNull();
	}

}
