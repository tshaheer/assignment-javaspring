package com.assignment.api.v1.rest;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.assignment.domain.Account;
import com.assignment.dto.StatementDto;
import com.assignment.security.AuthoritiesConstants;
import com.assignment.service.StatementService;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
class StatementControllerIT {

	private static final Long ACCOUNT_ID = 4L;
	private static final String FROM_AMOUNT = "256.292396032404";
	private static final String TO_AMOUNT = "967.410308637791";
	private static final String API_URL_STATEMENTS = "/v1/statements";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StatementService statementService;

	private List<StatementDto> statements = new ArrayList<>();

	@BeforeEach
	void setUp() {
		Account account = new Account();
		account.setId(ACCOUNT_ID);
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
	void givenAccountIdAndDateRange_whenSearchStatements_thenReturnStatementDtoList() throws Exception {
		given(statementService.searchStatements(ACCOUNT_ID, LocalDate.of(2018, 07, 05), LocalDate.of(2020, 11, 15)))
				.willReturn(statements);
		ResultActions response = mockMvc
				.perform(get(API_URL_STATEMENTS + "/{accountId}?fromDate=05.07.2018&toDate=15.11.2020", ACCOUNT_ID));
		response.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$.size()", CoreMatchers.is(statements.size())));
	}

	@Test
	void givenAccountIdAndInvalidFromDateFormat_whenSearchStatements_thenReturnBadRequest() throws Exception {
		given(statementService.searchStatements(ACCOUNT_ID, LocalDate.of(2018, 07, 05), LocalDate.of(2020, 11, 15)))
				.willReturn(statements);
		ResultActions response = mockMvc
				.perform(get(API_URL_STATEMENTS + "/{accountId}?fromDate=05-07-2018&toDate=15.11.2020", ACCOUNT_ID));
		response.andExpect(status().isBadRequest());
	}

	@Test
	void givenAccountIdAndInvalidToDateFormat_whenSearchStatements_thenReturnBadRequest() throws Exception {
		given(statementService.searchStatements(ACCOUNT_ID, LocalDate.of(2018, 07, 05), LocalDate.of(2020, 11, 15)))
				.willReturn(statements);
		ResultActions response = mockMvc
				.perform(get(API_URL_STATEMENTS + "/{accountId}?fromDate=05.07.2018&toDate=15-11-2020", ACCOUNT_ID));
		response.andExpect(status().isBadRequest());
	}

	@Test
	void givenAccountIdAndFromDateAfterToday_whenSearchStatements_thenReturnBadRequest() throws Exception {
		given(statementService.searchStatements(ACCOUNT_ID, LocalDate.of(4025, 07, 05), LocalDate.of(2020, 11, 15)))
				.willReturn(statements);
		ResultActions response = mockMvc
				.perform(get(API_URL_STATEMENTS + "/{accountId}?fromDate=05.07.4020&toDate=15.11.2020", ACCOUNT_ID));
		response.andExpect(status().isBadRequest());
	}

	@Test
	void givenAccountIdAndToDateAfterToday_whenSearchStatements_thenReturnBadRequest() throws Exception {
		given(statementService.searchStatements(ACCOUNT_ID, LocalDate.of(2018, 07, 05), LocalDate.of(4025, 11, 15)))
				.willReturn(statements);
		ResultActions response = mockMvc
				.perform(get(API_URL_STATEMENTS + "/{accountId}?fromDate=05.07.2018&toDate=15.11.4020", ACCOUNT_ID));
		response.andExpect(status().isBadRequest());
	}

	@Test
	void givenAccountIdAndToDateAfterToDate_whenSearchStatements_thenReturnBadRequest() throws Exception {
		given(statementService.searchStatements(ACCOUNT_ID, LocalDate.of(2018, 07, 05), LocalDate.of(2020, 11, 15)))
				.willReturn(statements);
		ResultActions response = mockMvc
				.perform(get(API_URL_STATEMENTS + "/{accountId}?fromDate=16.11.2020&toDate=15.11.2020", ACCOUNT_ID));
		response.andExpect(status().isBadRequest());
	}

	// Amount
	@Test
	void givenAccountIdAndAmountRange_whenSearchStatements_thenReturnStatementDtoList() throws Exception {
		given(statementService.searchStatements(ACCOUNT_ID, new BigDecimal(FROM_AMOUNT), new BigDecimal(TO_AMOUNT)))
				.willReturn(statements);
		ResultActions response = mockMvc.perform(get(
				API_URL_STATEMENTS + "/{accountId}?fromAmount=" + FROM_AMOUNT + "&toAmount=" + TO_AMOUNT, ACCOUNT_ID));
		response.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$.size()", CoreMatchers.is(statements.size())));
	}

	@Test
	void givenAccountIdAndInvalidFromAmount_whenSearchStatements_thenReturnBadRequest() throws Exception {
		given(statementService.searchStatements(ACCOUNT_ID, new BigDecimal(FROM_AMOUNT), new BigDecimal(TO_AMOUNT)))
				.willReturn(statements);
		ResultActions response = mockMvc.perform(get(
				API_URL_STATEMENTS + "/{accountId}?fromAmount=abc&toAmount=" + TO_AMOUNT, ACCOUNT_ID));
		response.andExpect(status().isBadRequest());
	}
	
	@Test
	void givenAccountIdAndInvalidToAmount_whenSearchStatements_thenReturnBadRequest() throws Exception {
		given(statementService.searchStatements(ACCOUNT_ID, new BigDecimal(FROM_AMOUNT), new BigDecimal(TO_AMOUNT)))
				.willReturn(statements);
		ResultActions response = mockMvc.perform(get(
				API_URL_STATEMENTS + "/{accountId}?fromAmount="+ FROM_AMOUNT +"&toAmount=xyz", ACCOUNT_ID));
		response.andExpect(status().isBadRequest());
	}

	@Test
	void givenAccountIdAndToAmountZero_whenSearchStatements_thenReturnBadRequest() throws Exception {
		given(statementService.searchStatements(ACCOUNT_ID, new BigDecimal(FROM_AMOUNT), new BigDecimal(TO_AMOUNT)))
				.willReturn(statements);
		ResultActions response = mockMvc.perform(get(
				API_URL_STATEMENTS + "/{accountId}?fromAmount=1&toAmount=0", ACCOUNT_ID));
		response.andExpect(status().isBadRequest());
	}
	
	@Test
	void givenAccountIdAndFromAmountGreaterThanToAmount_whenSearchStatements_thenReturnBadRequest() throws Exception {
		given(statementService.searchStatements(ACCOUNT_ID, new BigDecimal(FROM_AMOUNT), new BigDecimal(TO_AMOUNT)))
				.willReturn(statements);
		ResultActions response = mockMvc.perform(get(
				API_URL_STATEMENTS + "/{accountId}?fromAmount=12&toAmount=10", ACCOUNT_ID));
		response.andExpect(status().isBadRequest());
	}

	// only accountId
	@Test
	void givenAccountId_whenSearchStatements_thenReturnLastThreeMonthsStatementDtoList() throws Exception {
		given(statementService.searchStatements(ACCOUNT_ID)).willReturn(statements);
		ResultActions response = mockMvc.perform(get(API_URL_STATEMENTS + "/{accountId}", ACCOUNT_ID));
		response.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$.size()", CoreMatchers.is(statements.size())));
	}
	
	
}
