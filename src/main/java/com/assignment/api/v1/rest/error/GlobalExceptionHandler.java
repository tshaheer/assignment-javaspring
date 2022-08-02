package com.assignment.api.v1.rest.error;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Controller advice to translate the server side exceptions to client-friendly
 * json structures.
 * 
 * @author Shaheer
 * @since 1 August 2022
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Handles StatementSearchException. Created to encapsulate errors in statement
	 * search.
	 *
	 * @param ex the StatementSearchException
	 * @return the ApiErrorResponse object
	 */
	@ExceptionHandler({ StatementSearchException.class })
	public ResponseEntity<ApiErrorResponse> handleStatementSearchException(final StatementSearchException ex,
			final WebRequest request) {
		final List<String> errors = ex.getFieldErrors();
		final ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
				errors);
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<ApiErrorResponse> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex,
			final WebRequest request) {
		Class<?> requiredType = ex.getRequiredType();
		if (requiredType != null && requiredType.getTypeName().equals(BigDecimal.class.getName())) {
			final ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST, "Invalid format",
					ex.getName() + " is not a Number");
			return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
		}
		final String error = ex.getName() + " should be of type " + ex.getRequiredType();
		final ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler({ AuthenticationException.class })
	public ResponseEntity<ApiErrorResponse> handleAuthenticationException(final AuthenticationException ex,
			final WebRequest request) {
		final ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	// 500

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<ApiErrorResponse> handleAll(final Exception ex, final WebRequest request) {
		final ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
				ex.getLocalizedMessage(), "error occurred");
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

}
