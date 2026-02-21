package com.oop.library_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<Map<String, String>> handleValidationException(
			ValidationException ex
	) {

		Map<String, String> error = new HashMap<>();
		error.put("timestamp", String.valueOf(LocalDateTime.now()));
		error.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
		error.put("message", ex.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex
	) {

		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getFieldErrors().forEach(error ->
				errors.put(error.getField(), error.getDefaultMessage())
		);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	@ExceptionHandler(com.oop.library_management.exception.AuthenticationException.class)
	public ResponseEntity<Map<String, String>> handleAuthenticationException(
			com.oop.library_management.exception.AuthenticationException ex
	) {

		Map<String, String> error = new HashMap<>();
		error.put("timestamp", String.valueOf(LocalDateTime.now()));
		error.put("status", String.valueOf(HttpStatus.UNAUTHORIZED.value()));
		error.put("message", ex.getMessage());

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}

	@ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
	public ResponseEntity<Map<String, String>> handleSpringAuthenticationException(
			org.springframework.security.core.AuthenticationException ex
	) {

		Map<String, String> error = new HashMap<>();
		error.put("timestamp", String.valueOf(LocalDateTime.now()));
		error.put("status", String.valueOf(HttpStatus.UNAUTHORIZED.value()));
		error.put("message", "Invalid username or password");

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}
}
