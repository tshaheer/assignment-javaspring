package com.assignment.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.assignment.dto.StatementDto;

public interface StatementService {

	public List<StatementDto> searchStatements(Long accountId);
	public List<StatementDto> searchStatements(Long accountId, LocalDate fromDate, LocalDate toDate);
	public List<StatementDto> searchStatements(Long accountId, BigDecimal fromAmount, BigDecimal toAmount);
}
