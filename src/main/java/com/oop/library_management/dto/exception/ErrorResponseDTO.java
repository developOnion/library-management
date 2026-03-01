package com.oop.library_management.dto.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponseDTO {

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime timestamp;
	private int status;
	private String error;
	private String message;
	private Map<String, String> validationErrors;

	public ErrorResponseDTO() {
	}

	public ErrorResponseDTO(
			LocalDateTime timestamp,
			int status,
			String error,
			String message
	) {

		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
	}

	public ErrorResponseDTO(
			LocalDateTime timestamp,
			int status,
			String error,
			String message,
			Map<String, String> validationErrors
	) {

		this(timestamp, status, error, message);
		this.validationErrors = validationErrors;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, String> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(Map<String, String> validationErrors) {
		this.validationErrors = validationErrors;
	}
}
