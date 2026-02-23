package com.oop.library_management.controller;

import com.oop.library_management.model.Book;
import com.oop.library_management.service.BookService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/{id}")
	public Book getBookById(@PathVariable Long id) {
		return bookService.getBookById(id);
	}

	@PreAuthorize("hasAuthority('LIBRARIAN')")
	@PostMapping("/")
	public Book addBook(@RequestBody Book book) {
		return bookService.addBook(book);
	}
}
