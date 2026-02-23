package com.oop.library_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BookRequestDTO {

	@NotBlank(message = "Title is required")
	@Size(max = 255, message = "Title must be at most 255 characters")
	private String title;

	@Size(max = 20, message = "ISBN must be at most 20 characters")
	private String isbn;

	private
}
