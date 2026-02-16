package com.oop.library_management.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotBlank(message = "Title is required")
	@Size(max = 255, message = "Title must be at most 255 characters")
	private String title;

	@Column(unique = true, length = 20)
	@Size(max = 20, message = "ISBN must be at most 20 characters")
	private String isbn;

	@ManyToMany
	@JoinTable(
			name = "book_authors",
			joinColumns = @JoinColumn(name = "book_id"),
			inverseJoinColumns = @JoinColumn(name = "author_id")
	)
	private Set<Author> authors = new HashSet<>();

	@ManyToMany
	@JoinTable(
			name = "book_categories",
			joinColumns = @JoinColumn(name = "book_id"),
			inverseJoinColumns = @JoinColumn(name = "category_id")
	)
	private Set<Category> categories = new HashSet<>();

	@Column(name = "total_copies", nullable = false)
	@Min(value = 1, message = "Total copies must be at least 1")
	private int totalCopies;

	@Column(name = "available_copies", nullable = false)
	@Min(value = 0, message = "Available copies cannot be negative")
	private int availableCopies;

	protected Book() {}

	public Book(String title, int totalCopies) {
		this.title = title;
		this.totalCopies = totalCopies;
		this.availableCopies = totalCopies;
	}

	public Long getId() { return id; }
	public String getTitle() { return title; }
	public String getIsbn() { return isbn; }
	public Set<Author> getAuthors() { return authors; }
	public Set<Category> getCategories() { return categories; }
	public int getTotalCopies() { return totalCopies; }
	public int getAvailableCopies() { return availableCopies; }

	public void setTitle(String title) { this.title = title; }
	public void setIsbn(String isbn) { this.isbn = isbn; }
	public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }
	public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }

	// helper methods
	public void addAuthor(Author author) {
		authors.add(author);
	}

	public void addCategory(Category category) {
		categories.add(category);
	}
}
