package com.assignment.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.assignment.domain.Account;
import com.assignment.domain.Statement;
import com.assignment.domain.enums.AccountType;

@DataJpaTest
public class StatementRepositoryTests {
	
	@Autowired
	private StatementRepository statementRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	private Statement statement;
	
	@BeforeEach
	public void setUp() {
		Account account = new Account();
    	account.setId(1L);
    	account.setAccountType(AccountType.CURRENT);
    	account.setAccountNumber("0012250016004");
    	accountRepository.save(account);
    	
    	statement = new Statement();
    	statement.setId(1L);
    	statement.setDate("15.11.2020");
    	statement.setAmount("967.410308637791");
		statement.setAccount(account);
		statementRepository.save(statement);
	}
	
	@Test
	public void givenStatements_whenFindAll_thenStatementList() {
		List<Statement> statementList = statementRepository.findAll();
		assertThat(statementList).isNotNull();
		assertThat(statementList.size()).isEqualTo(1);
	}

}
