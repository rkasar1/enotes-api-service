package com.enotesApiService.exception;

import java.util.Map;

public class ValidationException extends RuntimeException {

	Map<String, Object> error;

	public ValidationException(Map<String, Object> error) {
		super("validation failed");
		this.error = error;
	}

	Map<String, Object> getErrors() {
		return error;
	}
}
