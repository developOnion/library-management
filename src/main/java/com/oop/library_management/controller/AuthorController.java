package com.oop.library_management.controller;

import com.oop.library_management.dto.AuthorResponseDTO;
import com.oop.library_management.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

	private final AuthorService authorService;

	public AuthorController(
			AuthorService authorService
	) {
		this.authorService = authorService;
	}

	@GetMapping
	public ResponseEntity<List<AuthorResponseDTO>> searchAuthorsByName(
			@RequestParam(required = false) String name
	) {

		List<AuthorResponseDTO> authors = authorService.searchAuthorsByName(name);

		return ResponseEntity.ok().body(authors);
	}

	@PreAuthorize("hasAuthority('LIBRARIAN')")
	@PostMapping
	public ResponseEntity<AuthorResponseDTO> createAuthor(
			@Valid @RequestBody AuthorResponseDTO authorRequestDTO
	) {

		AuthorResponseDTO createdAuthor = authorService.createAuthor(authorRequestDTO);

		return ResponseEntity.ok().body(createdAuthor);
	}

	public ResponseEntity<AuthorResponseDTO> getAuthorById(
			@PathVariable Long id
	) {

		AuthorResponseDTO author = authorService.getAuthorById(id);

		return ResponseEntity.ok().body(author);
	}
}
