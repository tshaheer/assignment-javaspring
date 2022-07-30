package com.assignment.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.assignment.domain.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * An Account.
 * 
 * @author Shaheer
 * @since 30 July 2022
 */
@Entity
@Table(name = "account")
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "account_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private AccountType accountType;

	@NotNull
	@Column(name = "account_number", nullable = false)
	private String accountNumber;

	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = { "account" }, allowSetters = true)
	private Set<Statement> statements = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Set<Statement> getStatements() {
		return statements;
	}

	public void setStatements(Set<Statement> statements) {
		this.statements = statements;
	}

	public Account addAccount(Statement statement) {
		this.statements.add(statement);
		statement.setAccount(this);
		return this;
	}

	public Account removeAccount(Statement statement) {
		this.statements.remove(statement);
		statement.setAccount(null);
		return this;
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
