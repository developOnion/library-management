package com.oop.library_management.dto.book;

import jakarta.validation.constraints.*;

import java.util.HashSet;
import java.util.Set;

public class BookRequestDTO {

	@NotBlank(message = "Title is required")
	@Size(max = 255, message = "Title must be at most 255 characters")
	private String title;

	@Size(max = 20, message = "ISBN must be at most 20 characters")
	private String isbn;

	@NotNull(message = "Total copies is required")
	@Min(value = 1, message = "Total copies must be at least 1")
	private Integer totalCopies;

	@NotEmpty(message = "At least one author is required")
	private Set<Long> authorIds = new HashSet<>();

	@NotEmpty(message = "At least one category is required")
	private Set<Long> categoryIds = new HashSet<>();

	public BookRequestDTO() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Integer getTotalCopies() {
		return totalCopies;
	}

	public void setTotalCopies(Integer totalCopies) {
		this.totalCopies = totalCopies;
	}

	public Set<Long> getAuthorIds() {
		return authorIds;
	}

	public void setAuthorIds(Set<Long> authorIds) {
		this.authorIds = authorIds;
	}

	public Set<Long> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(Set<Long> categoryIds) {
		this.categoryIds = categoryIds;
	}
}
