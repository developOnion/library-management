package com.oop.library_management.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "librarians")
public class Librarian extends User {

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private LibrarianPosition position;

	protected Librarian() {
		// for JPA
	}

	public Librarian(
			String username,
			String password,
			String firstName,
			String lastName,
			String email,
			Role role,
			LibrarianPosition position
	) {
		super(username, password, firstName, lastName, email, role);
		this.position = position;
	}

	public LibrarianPosition getPosition() {
		return position;
	}

	public void setPosition(LibrarianPosition position) {
		this.position = position;
	}
}
