package com.assignment.dto;

import javax.validation.constraints.NotNull;

/**
 * DTO  for storing a user's credentials.
 * 
 * @author Shaheer
 * @since 31 July 2022
 */
public class LoginDto {

    @NotNull
    private String username;

    @NotNull
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	@Override
	public String toString() {
		return "LoginDto [username=" + username + ", password=" + password + "]";
	}
}
