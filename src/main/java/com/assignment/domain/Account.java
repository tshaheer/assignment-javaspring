package com.assignment.domain;

import java.io.Serializable;

/**
 * An Account.
 * 
 * @author Shaheer
 * @since 30 July 2022
 */
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String accountType;

	private String accountNumber;
	
	public Account() {
	}

	public Account(Long id, String accountType, String accountNumber) {
		this.id = id;
		this.accountType = accountType;
		this.accountNumber = accountNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Account)) {
			return false;
		}
		return id != null && id.equals(((Account) o).id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", accountType=" + accountType + ", accountNumber=" + accountNumber + "]";
	}
}
