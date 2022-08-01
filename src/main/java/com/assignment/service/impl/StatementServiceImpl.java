package com.assignment.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.dao.StatementDao;
import com.assignment.domain.Statement;
import com.assignment.dto.StatementDto;
import com.assignment.service.StatementService;

/**
 * Service class for managing account statements.
 * 
 * @author Shaheer
 * @since 30 July 2022
 */
@Service
@Transactional
public class StatementServiceImpl implements StatementService {
	private final Logger log = LoggerFactory.getLogger(StatementServiceImpl.class);

	private final StatementDao statementDao;

	public StatementServiceImpl(StatementDao statementDao) {
		this.statementDao = statementDao;
	}

	@Override
	public List<StatementDto> searchStatements(Long accountId) {
		log.debug("Statement search started with accountId {} and without any parameter", accountId);
		return getLastThreeMonthStatements(accountId);
	}

	@Override
	public List<StatementDto> searchStatements(Long accountId, LocalDate fromDate, LocalDate toDate) {
		log.debug("Statement search started with accountId {} and with fromDate {}, toDate {}", accountId, fromDate,
				toDate);
		return searchByDateRange(accountId, fromDate, toDate);
	}

	@Override
	public List<StatementDto> searchStatements(Long accountId, BigDecimal fromAmount, BigDecimal toAmount) {
		log.debug("Statement search started with accountId {} and with fromAmount {}, toAmount {}", accountId,
				fromAmount, toAmount);
		return searchByAmountRange(accountId, fromAmount, toAmount);
	}

	private List<StatementDto> getLastThreeMonthStatements(Long accountId) {
		LocalDate today = LocalDate.now();
		LocalDate fromDate = today.minusMonths(3).withDayOfMonth(1);
		List<Statement> statements = statementDao.findAllByAccountId(accountId);
		return statements.stream().filter(fromDate != null ? filterByDateBetween(fromDate, today) : s -> true)
				.map(StatementDto::new).collect(Collectors.toList());
	}

	private List<StatementDto> searchByDateRange(Long accountId, LocalDate fromDate, LocalDate toDate) {
		List<Statement> statements = statementDao.findAllByAccountId(accountId);
		return statements.stream().filter(filterByDateBetween(fromDate, toDate)).map(StatementDto::new)
				.collect(Collectors.toList());
	}

	private List<StatementDto> searchByAmountRange(Long accountId, BigDecimal fromAmount, BigDecimal toAmount) {
		List<Statement> statements = statementDao.findAllByAccountId(accountId);
		return statements.stream().filter(filterByAmountBetween(fromAmount, toAmount)).map(StatementDto::new)
				.collect(Collectors.toList());
	}

	private Predicate<Statement> filterByDateBetween(LocalDate fromDate, LocalDate toDate) {
		return statement -> {
			LocalDate date = statement.getDate();
			return (date.isEqual(fromDate) || date.isAfter(fromDate))
					&& (date.isEqual(toDate) || date.isBefore(toDate));
		};
	}

	private Predicate<Statement> filterByAmountBetween(BigDecimal fromAmount, BigDecimal toAmount) {
		return statement -> {
			BigDecimal amount = statement.getAmount();
			return (amount.equals(fromAmount) || amount.compareTo(fromAmount) > 0)
					&& (amount.equals(toAmount) || amount.compareTo(toAmount) < 0);
		};
	}

}
