package com.assignment.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * Maps a row in the statement table to a Statement object 
 * 
 * @author Shaheer
 * @since 31 July 2022
 */
public class StatementRowMapper implements RowMapper<Statement> {
	
	private Account account;
	
	public StatementRowMapper(Account account) {
		this.account = account;
	}

	@Override
	public Statement mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Statement(rs.getLong("id"), rs.getString("datefield"), rs.getString("amount"), this.account);
	}

}
