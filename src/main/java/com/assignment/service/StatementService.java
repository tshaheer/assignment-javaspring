package com.assignment.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.domain.Statement;
import com.assignment.dto.StatementDto;
import com.assignment.dto.StatementSearchDto;
import com.assignment.repository.StatementRepository;

/**
 * Service class for managing account statements.
 * 
 * @author Shaheer
 * @since 30 July 2022
 */
@Service
@Transactional
public class StatementService {
	private final Logger log = LoggerFactory.getLogger(StatementService.class);

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	private final StatementRepository statementRepository;

	public StatementService(StatementRepository statementRepository) {
		this.statementRepository = statementRepository;
	}

	public List<StatementDto> searchStatements(Long accountId, StatementSearchDto statementSearchDto) {
		log.debug("Statement search started with data {}", statementSearchDto);
		List<StatementDto> statementDtos;
		if (statementSearchDto == null) {
			statementDtos = getLastThreeMonthStatements(accountId);
		} else {
			statementDtos = searchByDateAmountRange(accountId, statementSearchDto);
		}
		log.debug("Statement search completed with result {}", statementDtos);
		return statementDtos;
	}

	private List<StatementDto> getLastThreeMonthStatements(Long accountId) {
		LocalDate today = LocalDate.now();
		LocalDate fromDate = today.minusMonths(3).withDayOfMonth(1);
		List<Statement> statements = statementRepository.findAll();
		return statements.stream().filter(s -> Objects.equals(s.getAccount().getId(), accountId))
				.filter(fromDate != null ? filterByDateBetween(fromDate, today) : s -> true).map(StatementDto::new)
				.collect(Collectors.toList());
	}

	private List<StatementDto> searchByDateAmountRange(Long accountId, StatementSearchDto statementSearchDto) {
		List<Statement> statements = statementRepository.findAll();
		return statements.stream().filter(s -> Objects.equals(s.getAccount().getId(), accountId))
				.filter(statementSearchDto.getFromAmount() != null
						? filterByAmountBetween(statementSearchDto.getFromAmount(), statementSearchDto.getToAmount())
						: s -> true)
				.filter(statementSearchDto.getFromDate() != null
						? filterByDateBetween(statementSearchDto.getFromDate(), statementSearchDto.getToDate())
						: s -> true)
				.map(StatementDto::new).collect(Collectors.toList());
	}

	private Predicate<Statement> filterByDateBetween(LocalDate fromDate, LocalDate toDate) {
		return statement -> {
			LocalDate date = convertToDate(statement.getDate());
			return (date.isEqual(fromDate) || date.isAfter(fromDate))
					&& (date.isEqual(toDate) || date.isBefore(toDate));
		};
	}

	private Predicate<Statement> filterByAmountBetween(BigDecimal fromAmount, BigDecimal toAmount) {
		return statement -> {
			BigDecimal amount = new BigDecimal(statement.getAmount());
			return (amount.equals(fromAmount) || amount.compareTo(fromAmount) > 0)
					&& (amount.equals(toAmount) || amount.compareTo(toAmount) < 0);
		};
	}

	private LocalDate convertToDate(String dateInString) {
		return LocalDate.parse(dateInString, DATE_FORMATTER);
	}

}
