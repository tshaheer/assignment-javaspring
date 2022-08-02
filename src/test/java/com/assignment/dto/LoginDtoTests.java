package com.assignment.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class LoginDtoTests {

	final String DEFAULT_USERNAME = "user";
	final String DEFAULT_PASSWORD = "pass";

	@Test
	void test_state_is_correct() {
		LoginDto dto = new LoginDto();
		dto.setUsername(DEFAULT_USERNAME);
		dto.setPassword(DEFAULT_PASSWORD);

		assertThat(dto.getUsername()).isEqualTo(DEFAULT_USERNAME);
		assertThat(dto.getPassword()).isEqualTo(DEFAULT_PASSWORD);
	}
	
	@Test
	void test_toStringOverride() {
		LoginDto dto = new LoginDto();
		dto.setUsername(DEFAULT_USERNAME);
		dto.setPassword(DEFAULT_PASSWORD);
		
		assertThat(dto.toString()).contains(DEFAULT_USERNAME);
	}

}
