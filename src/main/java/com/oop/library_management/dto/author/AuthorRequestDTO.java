package com.oop.library_management.dto.author;

import com.oop.library_management.model.author.AuthorType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AuthorRequestDTO {

	@Size(max = 50, message = "First name must be at most 50 characters")
	private String firstName;

	@Size(max = 50, message = "Last name must be at most 50 characters")
	private String lastName;

	@NotNull
	private AuthorType type;

	public AuthorRequestDTO() {
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
