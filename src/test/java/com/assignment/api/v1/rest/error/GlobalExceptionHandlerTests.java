package com.assignment.api.v1.rest.error;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.assignment.security.SessionExistException;

class GlobalExceptionHandlerTests {
	
	@Test
	void givenStatementSearchException_whenHandleStatementSearchException_thenReturnApiErrorResponse() {
		String errorMsg = "Should be greater than zero";
		StatementSearchException exception = new StatementSearchException("Should be greater than zero",
				List.of("accountId should be greater than zero"));
		GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
		ResponseEntity<ApiErrorResponse> response = globalExceptionHandler.handleStatementSearchException(exception,
				null);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(errorMsg).isEqualTo(response.getBody().getMessage());
	}

	@Test
	void givenMethodArgumentTypeMismatchException_whenHandleMethodArgumentTypeMismatch_thenReturnApiErrorResponse() {
		String errorMsg = "Invalid format";
		NumberFormatException ne = new NumberFormatException(errorMsg);
		MethodArgumentTypeMismatchException expt = new MethodArgumentTypeMismatchException("abc", BigDecimal.class, "fromAmount", null, ne);
		
		GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
		ResponseEntity<ApiErrorResponse> response = globalExceptionHandler.handleMethodArgumentTypeMismatch(expt,
				null);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(errorMsg).isEqualTo(response.getBody().getMessage());
	}
	
	@Test
	void givenStringMethodArgumentTypeMismatchException_whenHandleMethodArgumentTypeMismatch_thenReturnApiErrorResponse() {
		String errorMsg = "Failed to convert value of type 'java.lang.String';";
		IllegalArgumentException ne = new IllegalArgumentException(errorMsg);
		MethodArgumentTypeMismatchException expt = new MethodArgumentTypeMismatchException("abc", null, "sample", null, ne);
		GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
		ResponseEntity<ApiErrorResponse> response = globalExceptionHandler.handleMethodArgumentTypeMismatch(expt,
				null);
		System.out.println(response.getBody().getMessage());
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertTrue(StringUtils.containsIgnoreCase(response.getBody().getMessage(), errorMsg));
	}

	@Test
	void givenAuthenticationException_whenHandleAuthenticationException_thenReturnApiErrorResponse() {
		String errorMsg = "User admin has active session. Please logout before authenticate.";
		AuthenticationException exception = new SessionExistException(errorMsg);
		GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
		ResponseEntity<ApiErrorResponse> response = globalExceptionHandler.handleAuthenticationException(exception,
				null);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(errorMsg).isEqualTo(response.getBody().getMessage());
	}
	
	@Test
	void givenOtherException_whenHandleAllException_thenReturnApiErrorResponse() {
		String errorMsg = "Object is null";
		NullPointerException exception = new NullPointerException(errorMsg);
		GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
		ResponseEntity<ApiErrorResponse> response = globalExceptionHandler.handleAll(exception,
				null);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		assertThat(errorMsg).isEqualTo(response.getBody().getMessage());
	}

}
