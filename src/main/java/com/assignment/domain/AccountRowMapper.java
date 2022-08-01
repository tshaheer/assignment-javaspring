package com.assignment.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class AccountRowMapper implements ResultSetExtractor<Account> {

	@Override
	public Account extractData(ResultSet rs) throws SQLException, DataAccessException {
		if (rs.next()) {
			return new Account(rs.getLong("id"), rs.getString("account_type"), rs.getString("account_number"));
		}
		return null;
	}
}
