package com.assignment.dao;

import java.util.Optional;

import com.assignment.domain.User;

public interface UserDao {
	public Optional<User> findByUsername(String username);
	
	public void addToken(String username, String token);

	public void removeToken(String username);
}
