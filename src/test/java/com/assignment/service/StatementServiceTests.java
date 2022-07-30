package com.assignment.service;

import static org.assertj.core.api.Assertions.assertThat;
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

import com.assignment.domain.Account;
import com.assignment.domain.Statement;
import com.assignment.domain.enums.AccountType;
import com.assignment.dto.StatementDto;
import com.assignment.dto.StatementSearchDto;
import com.assignment.repository.StatementRepository;

@ExtendWith(MockitoExtension.class)
class StatementServiceTests {

	@InjectMocks
	private StatementService statementService;

	@Mock
	private StatementRepository statementRepository;
	
	private StatementSearchDto statementSearchDto;

	private List<Statement> statements = new ArrayList<>();

	@BeforeEach
	public void setUp() {
		Account account = new Account();
		account.setId(4L);
		account.setAccountType(AccountType.CURRENT);
		account.setAccountNumber("0012250016004");

		Statement statement = new Statement();
		statement.setId(1L);
		statement.setDate("15.11.2020");
		statement.setAmount("967.410308637791");
		statement.setAccount(account);
		statements.add(statement);

		statement = new Statement();
		statement.setId(2L);
		statement.setDate("12.03.2020");
		statement.setAmount("256.292396032404");
		statement.setAccount(account);
		statements.add(statement);

		statement = new Statement();
		statement.setId(3L);
		statement.setDate("22.06.2020");
		statement.setAmount("386.908121686113");
		statement.setAccount(account);
		statements.add(statement);

		statement = new Statement();
		statement.setId(4L);
		statement.setDate("30.10.2019");
		statement.setAmount("798.090576128434");
		statement.setAccount(account);
		statements.add(statement);

		statement = new Statement();
		statement.setId(5L);
		statement.setDate("05.07.2018");
		statement.setAmount("501.921910891848");
		statement.setAccount(account);
		statements.add(statement);
		
		statementSearchDto = new StatementSearchDto();
		statementSearchDto.setAccountId(4L);
		statementSearchDto.setFromDate(LocalDate.of(2018, 07, 04));
		statementSearchDto.setToDate(LocalDate.of(2020, 11, 16));
		statementSearchDto.setFromAmount(new BigDecimal("257.292396032404"));
		statementSearchDto.setToAmount(new BigDecimal("966.410308637791"));
	}

	@Test
	void givenAllSearchParameters_whenStatementSearch_thenReturnStatementList() {
		given(statementRepository.findAll()).willReturn(statements);
		List<StatementDto> statementDtos = statementService.searchByDateAmountRange(statementSearchDto);
		assertThat(statementDtos).hasSize(3);
	}

	@Test
	void givenAllSearchParametersWithNonExistAccountId_whenStatementSearch_thenReturnEmptyStatementList() {
		statementSearchDto.setAccountId(6L);
		given(statementRepository.findAll()).willReturn(statements);
		List<StatementDto> statementDtos = statementService.searchByDateAmountRange(statementSearchDto);
		assertThat(statementDtos).isEmpty();
	}
	
	@Test
	void givenSearchParametersWithoutAmountRange_whenStatementSearch_thenReturnStatementList() {
		statementSearchDto.setFromAmount(null);
		statementSearchDto.setToAmount(null);
		given(statementRepository.findAll()).willReturn(statements);
		List<StatementDto> statementDtos = statementService.searchByDateAmountRange(statementSearchDto);
		assertThat(statementDtos).hasSize(5);
	}
	
	@Test
	void givenSearchParametersWithoutDateRange_whenStatementSearch_thenReturnStatementList() {
		statementSearchDto.setFromDate(null);
		statementSearchDto.setToDate(null);
		given(statementRepository.findAll()).willReturn(statements);
		List<StatementDto> statementDtos = statementService.searchByDateAmountRange(statementSearchDto);
		assertThat(statementDtos).hasSize(3);
	}
	
//	@Test
//	void givenSearchParametersWithoutAccountId_whenStatementSearch_thenReturnBadRequest() {
//		
//	}
//	
//	@Test
//	void givenSearchParametersWithInvalidDateRange_whenStatementSearch_thenReturnBadRequest() {
//		
//	}
//	
//	@Test
//	void givenSearchParametersWithInvalidAmountRange_whenStatementSearch_thenReturnBadRequest() {
//		
//	}
}
