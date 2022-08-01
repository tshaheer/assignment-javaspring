package com.assignment.api.v1.rest;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.dto.LoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Integration tests for the {@link UserJWTController} REST controller.
 * 
 * @author Shaheer
 * @since 1 August 2022
 */
@AutoConfigureMockMvc
@SpringBootTest
class UserJWTControllerIT {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@Transactional
	void testAuthorize() throws Exception {
		LoginDto login = new LoginDto();
		login.setUsername("user");
		login.setPassword("user");
		mockMvc.perform(post("/v1/authenticate").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(login))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id_token").isString()).andExpect(jsonPath("$.id_token").isNotEmpty())
				.andExpect(header().string("Authorization", not(nullValue())))
				.andExpect(header().string("Authorization", not(is(emptyString()))));
	}

	@Test
	void testAuthorizeFails() throws Exception {
		LoginDto login = new LoginDto();
		login.setUsername("wrong-user");
		login.setPassword("wrong password");
		mockMvc.perform(post("/v1/authenticate").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(login))).andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.id_token").doesNotExist()).andExpect(header().doesNotExist("Authorization"));
	}
}