package com.oop.library_management.dto.auth;

public class AuthResponseDTO {

	private String token;

	public AuthResponseDTO() {
	}

	public AuthResponseDTO(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}
}
