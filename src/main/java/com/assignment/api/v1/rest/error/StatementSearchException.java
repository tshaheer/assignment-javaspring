package com.assignment.api.v1.rest.error;

import java.util.List;

public class StatementSearchException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final List<String> fieldErrors;

	public StatementSearchException(String message, final List<String> fieldErrors) {
		super(message);
		this.fieldErrors = fieldErrors;
	}

	public StatementSearchException(String message, Throwable ex, final List<String> fieldErrors) {
		super(message, ex);
		this.fieldErrors = fieldErrors;
	}

	public List<String> getFieldErrors() {
		return fieldErrors;
	}
}