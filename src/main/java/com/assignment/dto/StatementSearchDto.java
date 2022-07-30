package com.assignment.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

/**
 * A DTO representing a statement search required data.
 * 
 * @author Shaheer
 * @since 30 July 2022
 */
public class StatementSearchDto {

	@NotNull
	private Long accountId;
	
	private LocalDate fromDate;
	
	private LocalDate toDate;
	
	private BigDecimal fromAmount;
	
	private BigDecimal toAmount;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public BigDecimal getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(BigDecimal fromAmount) {
		this.fromAmount = fromAmount;
	}

	public BigDecimal getToAmount() {
		return toAmount;
	}

	public void setToAmount(BigDecimal toAmount) {
		this.toAmount = toAmount;
	}

	@Override
	public String toString() {
		return "StatementSearchDto [accountId=" + accountId + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", fromAmount=" + fromAmount + ", toAmount=" + toAmount + "]";
	}
}
