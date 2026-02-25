package com.oop.library_management.dto.book;

import com.oop.library_management.dto.author.AuthorResponseDTO;
import com.oop.library_management.dto.category.CategoryResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class BookResponseDTO {

	private Long id;
	private String title;
	private String isbn;
	private int totalCopies;
	private int availableCopies;
	private List<AuthorResponseDTO> authors = new ArrayList<>();
	private List<CategoryResponseDTO> categories = new ArrayList<>();

	public BookResponseDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public int getTotalCopies() {
		return totalCopies;
	}

	public void setTotalCopies(int totalCopies) {
		this.totalCopies = totalCopies;
	}

	public int getAvailableCopies() {
		return availableCopies;
	}

	public void setAvailableCopies(int availableCopies) {
		this.availableCopies = availableCopies;
	}

	public List<AuthorResponseDTO> getAuthors() {
		return authors;
	}

	public void setAuthors(List<AuthorResponseDTO> authors) {
		this.authors = authors;
	}

	public List<CategoryResponseDTO> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryResponseDTO> categories) {
		this.categories = categories;
	}
}
