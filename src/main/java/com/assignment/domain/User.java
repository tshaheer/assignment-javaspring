package com.assignment.domain;

import java.io.Serializable;

/**
 * A User.
 * 
 * @author Shaheer
 * @since 1 August 2022
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;

	private String username;

	private String password;

	private String token;

	private boolean sessionActive;

	public User() {
	}

	public User(Long id, String username, String password, String token, boolean sessionActive) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.token = token;
		this.sessionActive = sessionActive;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isSessionActive() {
		return sessionActive;
	}

	public void setSessionActive(boolean sessionActive) {
		this.sessionActive = sessionActive;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof User)) {
			return false;
		}
		return id != null && id.equals(((User) o).id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", token=" + token + ", sessionActive=" + sessionActive + "]";
	}

}
