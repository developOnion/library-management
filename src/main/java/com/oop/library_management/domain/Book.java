package com.oop.library_management.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 255)
	private String title;

	@Column(unique = true, length = 20)
	private String isbn;

	@ManyToOne(optional = false)
	@JoinColumn(name = "author_id", nullable = false)
	private Author author;

	@ManyToOne(optional = false)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@Column(name = "total_copies", nullable = false)
	private int totalCopies;

	@Column(name = "copies_available", nullable = false)
	private int copiesAvailable;

	protected Book() {
		// Required by JPA
	}

	public Book(String title, Author author, Category category, int copiesAvailable) {
		this.title = title;
		this.author = author;
		this.category = category;
		this.copiesAvailable = copiesAvailable;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getIsbn() {
		return isbn;
	}

	public Author getAuthor() {
		return author;
	}

	public Category getCategory() {
		return category;
	}

	public int getCopiesAvailable() {
		return copiesAvailable;
	}

	public int getTotalCopies() {
		return totalCopies;
	}

	public void setTotalCopies(int totalCopies) {
		this.totalCopies = totalCopies;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void setCopiesAvailable(int copiesAvailable) {
		this.copiesAvailable = copiesAvailable;
	}
}

