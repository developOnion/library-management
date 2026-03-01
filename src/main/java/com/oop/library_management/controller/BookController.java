package com.oop.library_management.controller;

import com.oop.library_management.dto.book.BookRequestDTO;
import com.oop.library_management.dto.book.BookResponseDTO;
import com.oop.library_management.model.common.PageResponse;
import com.oop.library_management.service.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@Tag(name = "Book Management", description = "Endpoints for managing books in the library")
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookResponseDTO> getBookById(
			@PathVariable Long id
	) {

		BookResponseDTO book = bookService.getBookById(id);
		return ResponseEntity.ok().body(book);
	}

	@GetMapping
	public ResponseEntity<PageResponse<BookResponseDTO>> findAllBooks(
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "size", defaultValue = "10", required = false) int size
	) {

		PageResponse<BookResponseDTO> books = bookService.findAllBooks(page, size);

		return ResponseEntity.ok().body(books);
	}

	@PreAuthorize("hasAuthority('LIBRARIAN')")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<BookResponseDTO> addBook(
			@Valid @RequestBody BookRequestDTO bookRequestDTO
	) {

		BookResponseDTO createdBook = bookService.createBook(bookRequestDTO);

		return ResponseEntity.ok().body(createdBook);
	}

	@PreAuthorize("hasAuthority('LIBRARIAN')")
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> deleteBook(
			@PathVariable Long id
	) {

		bookService.deleteBook(id);

		return ResponseEntity.noContent().build();
	}
}
