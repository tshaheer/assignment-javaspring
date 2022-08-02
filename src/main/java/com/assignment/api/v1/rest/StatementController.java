package com.assignment.api.v1.rest;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.api.v1.rest.validator.StatementSearchValidator;
import com.assignment.dto.StatementDto;
import com.assignment.security.AuthoritiesConstants;
import com.assignment.service.StatementService;

/**
 * REST controller for managing {@link com.assignment.domain.Statement}.
 * 
 * @author Shaheer
 * @since 30 July 2022
 */
@RestController
@RequestMapping("/v1/statements")
public class StatementController {

	private final Logger log = LoggerFactory.getLogger(StatementController.class);

	private final StatementService statementService;
	
	public StatementController(StatementService statementService) {
		this.statementService = statementService;
	}

	@GetMapping(value = "/{accountId}", params = { "fromDate", "toDate" })
	@PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
	public List<StatementDto> searchStatements(@PathVariable(value = "accountId") final String accountId,
			@RequestParam(value = "fromDate") final String fromDate,
			@RequestParam(value = "toDate") final String toDate) {
		log.debug("REST request to search statements : accountId: {}, formDate: {}, toDate: {}", accountId, fromDate,
				toDate);
		StatementSearchValidator validator = new StatementSearchValidator();
		validator.validateDates(accountId, fromDate, toDate);
		return statementService.searchStatements(validator.getAccountId(), validator.getFromDate(),validator.getToDate());
	}

	@GetMapping(value = "/{accountId}", params = { "fromAmount", "toAmount" })
	@PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
	public List<StatementDto> searchStatements(@PathVariable(value = "accountId") final String accountId,
			@RequestParam(value = "fromAmount") final BigDecimal fromAmount,
			@RequestParam(value = "toAmount") final BigDecimal toAmount) {
		log.debug("REST request to search statements : accountId: {}, fromAmount: {}, toAmount: {}", accountId,
				fromAmount, toAmount);
		StatementSearchValidator validator = new StatementSearchValidator();
		validator.validateAmounts(accountId, fromAmount, toAmount);
		return statementService.searchStatements(validator.getAccountId(), fromAmount, toAmount);
	}

	@GetMapping(value = "/{accountId}")
	@PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.ADMIN + "\",\"" + AuthoritiesConstants.USER +"\" )")
	public List<StatementDto> searchStatements(@PathVariable(value = "accountId") final String accountId) {
		log.debug("REST request to get last three month statements for account : accountId: {}", accountId);
		StatementSearchValidator validator = new StatementSearchValidator();
		validator.validateAccountId(accountId);
		return statementService.searchStatements(validator.getAccountId());
	}

}
