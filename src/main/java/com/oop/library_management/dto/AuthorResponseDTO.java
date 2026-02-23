package com.oop.library_management.dto;

import com.oop.library_management.model.AuthorType;

public class AuthorResponseDTO {

	private Long id;
	private String fullName;
	private String firstName;
	private String lastName;
	private AuthorType type;

	public AuthorResponseDTO() {
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public AuthorType getType() {
		return type;
	}

	public void setType(AuthorType type) {
		this.type = type;
	}
}
