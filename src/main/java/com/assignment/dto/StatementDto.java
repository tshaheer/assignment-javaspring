package com.assignment.dto;

import org.apache.commons.lang3.StringUtils;

import com.assignment.domain.Statement;
import com.assignment.util.DateUtil;

/**
 * A DTO representing a statement, with account information.
 * 
 * @author Shaheer
 * @since 30 July 2022
 */
public class StatementDto {
	
	private String accountNumber;
	
	private String accountType;
	
	private String date;
	
	private String amount;

	public StatementDto() {
	}
	
	public StatementDto(String accountNumber, String accountType, String date, String amount) {
		super();
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.date = date;
		this.amount = amount;
	}

	public StatementDto(Statement statement) {
		this.accountNumber = maskNumber(statement.getAccount().getAccountNumber());
		this.accountType = statement.getAccount().getAccountType();
		this.date = statement.getDate().format(DateUtil.DATE_FORMATTER);
		this.amount = statement.getAmount().toString();
	}
	
	private String maskNumber(final String accNum) {
		final int start = 0;
	    final int end = accNum.length() - 2;
	    final String overlay = StringUtils.repeat("X", end - start);
	    return StringUtils.overlay(accNum, overlay, start, end);
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public String getDate() {
		return date;
	}

	public String getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "StatementDto [accountNumber=" + accountNumber + ", accountType=" + accountType + ", date=" + date
				+ ", amount=" + amount + "]";
	}
}
