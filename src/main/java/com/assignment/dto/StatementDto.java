package com.assignment.dto;

import com.assignment.domain.Statement;

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

	public StatementDto(Statement statement) {
		this.accountNumber = statement.getAccount().getAccountNumber();
		this.accountType = statement.getAccount().getAccountType().toString();
		this.date = statement.getDate();
		this.amount = statement.getAmount();
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
}
