package com.oop.library_management.model.book;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 100)
	@NotBlank(message = "Category name is required")
	@Size(max = 100, message = "Category name must be at most 100 characters")
	private String name;

	protected Category() {
		// Required by JPA
	}

	public Category(String name) {
		this.name = name;
	}

	public Category(String name, String description) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

