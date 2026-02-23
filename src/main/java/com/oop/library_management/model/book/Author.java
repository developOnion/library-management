package com.oop.library_management.model.book;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "authors")
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "full_name", nullable = false, length = 100)
	@NotBlank(message = "Full name is required")
	@Size(max = 100, message = "Full name must be at most 100 characters")
	private String fullName;

	@Column(name = "first_name", length = 50)
	@Size(max = 50, message = "First name must be at most 50 characters")
	private String firstName;

	@Column(name = "last_name", length = 50)
	@Size(max = 50, message = "Last name must be at most 50 characters")
	private String lastName;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@NotNull(message = "Author type is required")
	private AuthorType type;

	public Author() {
	}

	public Author(
			String firstName,
			String lastName,
			AuthorType type
	) {

		this.firstName = firstName;
		this.lastName = lastName;

		if (firstName != null && lastName != null) {
			this.fullName = firstName + " " + lastName;
		} else if (firstName != null) {
			this.fullName = firstName;
		} else if (lastName != null) {
			this.fullName = lastName;
		} else {
			this.fullName = "Unknown Author";
		}

		this.type = type;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public AuthorType getType() {
		return type;
	}

	public void setType(AuthorType type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
