package com.oop.library_management.dto.category;

public class CategoryResponseDTO {

	private Long id;
	private String name;

	public CategoryResponseDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
