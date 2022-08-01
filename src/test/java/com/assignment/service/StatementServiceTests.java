package com.assignment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.assignment.dao.StatementDao;
import com.assignment.domain.Account;
import com.assignment.domain.Statement;
import com.assignment.dto.StatementDto;
import com.assignment.service.impl.StatementServiceImpl;

@ExtendWith(MockitoExtension.class)
class StatementServiceTests {

	private static final Long ACCOUNT_ID = 4L;
	private static final LocalDate FROM_DATE = LocalDate.of(2018, 07, 05);
	private static final LocalDate TO_DATE = LocalDate.of(2020, 11, 15);
	private static final BigDecimal FROM_AMOUNT = new BigDecimal("257.292396032404");
	private static final BigDecimal TO_AMOUNT = new BigDecimal("966.410308637791");

	@InjectMocks
	private StatementServiceImpl statementService;

	@Mock
	private StatementDao statementDao;

	private List<Statement> statements = new ArrayList<>();

	@BeforeEach
	void setUp() {
		Account account = new Account();
		account.setId(ACCOUNT_ID);
		account.setAccountType("current");
		account.setAccountNumber("0012250016004");

		Statement statement = new Statement(1L, "15.11.2020", "967.410308637791", account);
		statements.add(statement);

		statement = new Statement(2L, "12.03.2020", "256.292396032404", account);
		statements.add(statement);

		statement = new Statement(3L, "22.06.2020", "386.908121686113", account);
		statements.add(statement);

		statement = new Statement(4L, "30.10.2019", "798.090576128434", account);
		statements.add(statement);

		statement = new Statement(5L, "05.07.2018", "501.921910891848", account);
		statements.add(statement);

		statement = new Statement(6L, "05.07.2022", "501.921910891848", account);
		statements.add(statement);
	}
	
	@Test
	void givenAccountId_whenStatementSearch_thenReturnLastThreeMonthStatementList() {
		given(statementDao.findAllByAccountId(ACCOUNT_ID)).willReturn(statements);
		List<StatementDto> statementDtos = statementService.searchStatements(ACCOUNT_ID);
		assertThat(statementDtos).hasSize(1);
	}

	@Test
	void givenNonExistAccountId_whenStatementSearch_thenReturnEmptyStatementList() {
		given(statementDao.findAllByAccountId(Long.MAX_VALUE)).willReturn(Collections.emptyList());
		List<StatementDto> statementDtos = statementService.searchStatements(Long.MAX_VALUE);
		assertThat(statementDtos).isEmpty();
	}

	@Test
	void givenDateRange_whenStatementSearch_thenReturnStatementList() {
		given(statementDao.findAllByAccountId(ACCOUNT_ID)).willReturn(statements);
		List<StatementDto> statementDtos = statementService.searchStatements(ACCOUNT_ID, FROM_DATE, TO_DATE);
		assertThat(statementDtos).hasSize(5);
	}

	@Test
	void givenAmountRange_whenStatementSearch_thenReturnStatementList() {
		given(statementDao.findAllByAccountId(ACCOUNT_ID)).willReturn(statements);
		List<StatementDto> statementDtos = statementService.searchStatements(ACCOUNT_ID, FROM_AMOUNT, TO_AMOUNT);
		assertThat(statementDtos).hasSize(4);
	}
}
