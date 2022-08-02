package com.assignment.api.v1.rest.error;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;

/**
 * Hold relevant information about errors that happen during REST calls
 * 
 * @author Shaheer
 * @since 1 August 2022
 *
 */
public class ApiErrorResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiErrorResponse(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
        errors = Collections.emptyList();
    }
    
    public ApiErrorResponse(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiErrorResponse(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }

	public HttpStatus getStatus() {
		return status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public List<String> getErrors() {
		return errors;
	}

	@Override
	public String toString() {
		return "ApiErrorResponse [status=" + status + ", message=" + message + ", errors=" + errors + "]";
	}
}
