package com.assignment.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class UserRowMapper implements ResultSetExtractor<User> {

	@Override
	public User extractData(ResultSet rs) throws SQLException, DataAccessException {
		if (rs.next()) {
			return new User(rs.getLong("id"), rs.getString("username"), rs.getString("password"), rs.getString("token"),
					rs.getBoolean("session_active"));
		}
		return null;
	}

}
