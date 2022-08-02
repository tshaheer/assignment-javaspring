package com.assignment.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.assignment.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Statement.
 * 
 * @author Shaheer
 * @since 30 July 2022
 */
public class Statement implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;

	private LocalDate date;

	private BigDecimal amount;

	@JsonIgnoreProperties(value = { "statements" }, allowSetters = true)
	private Account account;

	public Statement() {
	}
	
	public Statement(Long id, String dateInString, String amountInString, Account account) {
		super();
		this.id = id;
		this.date = DateUtil.convertToDate(dateInString);
		this.amount = new BigDecimal(amountInString);
		this.account = account;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Statement)) {
			return false;
		}
		return id != null && id.equals(((Statement) o).id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	@Override
	public String toString() {
		return "Statement [id=" + id + ", account=" + account + ", date=" + date + ", amount=" + amount + "]";
	}

}
