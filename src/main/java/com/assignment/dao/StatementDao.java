package com.assignment.dao;

import java.util.List;

import com.assignment.domain.Statement;

public interface StatementDao {
	public List<Statement> findAllByAccountId(Long accountId);
}
