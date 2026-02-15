package com.oop.library_management.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "members")
public class Member extends User {

	protected Member() {
	}

	public Member(
			String username,
			String password,
			String firstName,
			String lastName,
			String email,
			Role role
	) {
		super(username, password, firstName, lastName, email, role);
	}

	// derived from id
	public String getMembershipNumber() {

		if (getId() == null) {
			return null; // not yet persisted
		}
		return "MEM-" + String.format("%05d", getId());
	}
}
