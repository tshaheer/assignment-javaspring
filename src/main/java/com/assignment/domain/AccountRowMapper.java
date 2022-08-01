package com.assignment.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AccountRowMapper implements RowMapper<Account> {

	@Override
	public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Account(rs.getLong("id"), rs.getString("account_type"), rs.getString("account_number"));
	}

}
