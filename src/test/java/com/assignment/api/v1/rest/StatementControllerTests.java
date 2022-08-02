package com.assignment.api.v1.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.assignment.api.v1.rest.error.StatementSearchException;
import com.assignment.domain.Account;
import com.assignment.dto.StatementDto;
import com.assignment.service.StatementService;

@ExtendWith(MockitoExtension.class)
class StatementControllerTests {

	private static final String ACCOUNT_ID = "4";
	private static final String FROM_DATE = "05.07.2018";
	private static final String TO_DATE = "15.11.2020";
	private static final BigDecimal FROM_AMOUNT = new BigDecimal("256.292396032404");
	private static final BigDecimal TO_AMOUNT = new BigDecimal("967.410308637791");
	private static final BigDecimal AMOUNT_LESSTHAN_ZERO = new BigDecimal("-1");
	private static final BigDecimal AMOUNT_GREATER_THAN_TOAMOUNT = TO_AMOUNT.add(TO_AMOUNT);

	@Mock
	private StatementService statementService;

	@InjectMocks
	private StatementController statementController;

	private List<StatementDto> statements = new ArrayList<>();

	@BeforeEach
	void setUp() {
		Account account = new Account();
		account.setId(Long.parseLong(ACCOUNT_ID));
		account.setAccountType("current");
		account.setAccountNumber("0012250016004");

		StatementDto statement = new StatementDto(account.getAccountNumber(), account.getAccountType(), "15.11.2020",
				"967.410308637791");
		statements.add(statement);

		statement = new StatementDto(account.getAccountNumber(), account.getAccountType(), "12.03.2020",
				"256.292396032404");
		statements.add(statement);

		statement = new StatementDto(account.getAccountNumber(), account.getAccountType(), "22.06.2020",
				"386.908121686113");
		statements.add(statement);
	}

	// Date
	@Test
	void givenAccountIdAndDateRange_whenSearchStatements_thenReturnStatementDtoList() {
		given(statementService.searchStatements(Long.parseLong(ACCOUNT_ID), LocalDate.of(2018, 07, 05),
				LocalDate.of(2020, 11, 15))).willReturn(statements);
		List<StatementDto> statements = statementController.searchStatements(ACCOUNT_ID, FROM_DATE, TO_DATE);
		assertThat(statements).isNotEmpty();
	}

	@Test
	void givenInvalidAccountIdAndDateRange_whenSearchStatements_thenReturnStatementSearchException() {
		StatementSearchException searchException = assertThrows(StatementSearchException.class,
				() -> statementController.searchStatements("" + Long.MIN_VALUE, FROM_DATE, TO_DATE));
		assertEquals("Should be greater than zero", searchException.getMessage());
	}

	@Test
	void givenCharacterAccountIdAndDateRange_whenSearchStatements_thenReturnStatementSearchException() {
		StatementSearchException searchException = assertThrows(StatementSearchException.class,
				() -> statementController.searchStatements("abc", FROM_DATE, TO_DATE));
		assertEquals("Should be a number", searchException.getMessage());
	}

	@Test
	void givenAccountIdAndInvalidToDateFormat_whenSearchStatements_thenReturnStatementSearchException() {
		StatementSearchException searchException = assertThrows(StatementSearchException.class,
				() -> statementController.searchStatements(ACCOUNT_ID, FROM_DATE, "15-11-2020"));
		assertEquals("Invalid date format. Valid date format is dd.MM.yyyy", searchException.getMessage());
	}

	@Test
	void givenAccountIdAndFromDateAfterToday_whenSearchStatements_thenReturnStatementSearchException() {
		StatementSearchException searchException = assertThrows(StatementSearchException.class,
				() -> statementController.searchStatements(ACCOUNT_ID, "05.07.3018", TO_DATE));
		assertEquals("Date must be less than or equal to today", searchException.getMessage());
	}

	@Test
	void givenAccountIdAndToDateAfterToday_whenSearchStatements_thenReturnStatementSearchException() {
		StatementSearchException searchException = assertThrows(StatementSearchException.class,
				() -> statementController.searchStatements(ACCOUNT_ID, FROM_DATE, "15.11.3020"));
		assertEquals("Date must be less than or equal to today", searchException.getMessage());
	}

	@Test
	void givenAccountIdAndFromDateAfterToDate_whenSearchStatements_thenReturnStatementSearchException() {
		StatementSearchException searchException = assertThrows(StatementSearchException.class,
				() -> statementController.searchStatements(ACCOUNT_ID, "16.11.2020", TO_DATE));
		assertEquals("fromDate should be less than toDate", searchException.getMessage());
	}

	// Amount
	@Test
	void givenAccountIdAndAmountRange_whenSearchStatements_thenReturnStatementDtoList() {
		given(statementService.searchStatements(Long.parseLong(ACCOUNT_ID), FROM_AMOUNT, TO_AMOUNT))
				.willReturn(statements);
		List<StatementDto> statements = statementController.searchStatements(ACCOUNT_ID, FROM_AMOUNT, TO_AMOUNT);
		assertThat(statements).isNotEmpty();
	}

	@Test
	void givenInvalidAccountIdAndAmountRange_whenSearchStatements_thenReturnStatementSearchException() {
		StatementSearchException searchException = assertThrows(StatementSearchException.class,
				() -> statementController.searchStatements("" + Long.MIN_VALUE, FROM_AMOUNT, TO_AMOUNT));
		assertEquals("Should be greater than zero", searchException.getMessage());
	}

	@Test
	void givenAccountIdAndFromAmountLessThanZero_whenSearchStatements_thenReturnStatementSearchException() {
		StatementSearchException searchException = assertThrows(StatementSearchException.class,
				() -> statementController.searchStatements(ACCOUNT_ID, AMOUNT_LESSTHAN_ZERO, TO_AMOUNT));
		assertEquals("Value should be greater than zero", searchException.getMessage());
	}

	@Test
	void givenAccountIdAndToAmountLessThanZero_whenSearchStatements_thenReturnStatementSearchException() {
		StatementSearchException searchException = assertThrows(StatementSearchException.class,
				() -> statementController.searchStatements(ACCOUNT_ID, FROM_AMOUNT, AMOUNT_LESSTHAN_ZERO));
		assertEquals("Value should be greater than zero", searchException.getMessage());
	}

	@Test
	void givenAccountIdAndFromAmountGreaterThanToAmount_whenSearchStatements_thenReturnStatementSearchException() {
		StatementSearchException searchException = assertThrows(StatementSearchException.class,
				() -> statementController.searchStatements(ACCOUNT_ID, AMOUNT_GREATER_THAN_TOAMOUNT , TO_AMOUNT));
		assertEquals("fromAmount should be less than toAmount", searchException.getMessage());
	}

	// only accountId
	@Test
	void givenAccountId_whenSearchStatements_thenReturnLastThreeMonthsStatementDtoList() {
		given(statementService.searchStatements(Long.parseLong(ACCOUNT_ID))).willReturn(statements);
		List<StatementDto> statements = statementController.searchStatements(ACCOUNT_ID);
		assertThat(statements).isNotEmpty();
	}

}
