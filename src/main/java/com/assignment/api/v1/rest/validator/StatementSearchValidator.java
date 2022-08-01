package com.assignment.api.v1.rest.validator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import com.assignment.api.v1.rest.error.StatementSearchException;
import com.assignment.util.DateUtil;

public class StatementSearchValidator {

	private LocalDate fromDate;

	private LocalDate toDate;

	public void validateDates(String fDate, String tDate) {
		validateDateFormat(fDate, tDate);
		LocalDate today = LocalDate.now();
		if (fromDate.isAfter(today)) {
			throw new StatementSearchException("Date must be less than or equal to today",
					List.of("fromDate must be less than or equal to today"));
		}
		if (toDate.isAfter(today)) {
			throw new StatementSearchException("Date must be less than or equal to today",
					List.of("toDate must be less than or equal to today"));
		}
		if (fromDate.isAfter(toDate)) {
			throw new StatementSearchException("fromDate should be less than toDate",
					List.of("fromDate should be less than toDate"));
		}
	}

	public void validateAmounts(BigDecimal fAmt, BigDecimal tAmt) {
		if (fAmt.signum() <= 0) {
			throw new StatementSearchException("Value should be greater than zero", List.of("fromAmount should be greater than zero"));
		}
		if (tAmt.signum() <= 0) {
			throw new StatementSearchException("Value should be greater than zero", List.of("toAmount should be greater than zero"));
		}
		if (fAmt.compareTo(tAmt) > 0) {
			throw new StatementSearchException("fromAmount should be less than toAmount", List.of("fromAmount should be less than toAmount"));
		}
	}

	private void validateDateFormat(String fDate, String tDate) {
		try {
			fromDate = DateUtil.convertToDate(fDate);
			toDate = DateUtil.convertToDate(tDate);
		} catch (DateTimeParseException e) {
			throw new StatementSearchException("Invalid date format. Valid date format is dd.MM.yyyy", e,
					List.of("Invalid date format. Valid date format is dd.MM.yyyy"));
		}
	}
}
