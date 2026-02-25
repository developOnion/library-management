package com.oop.library_management.controller;

import com.oop.library_management.dto.author.AuthorRequestDTO;
import com.oop.library_management.dto.author.AuthorResponseDTO;
import com.oop.library_management.service.AuthorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@Tag(name = "Author Management", description = "Endpoints for managing authors in the library")
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
	@GetMapping("/{id}")
	public ResponseEntity<AuthorResponseDTO> getAuthorById(
			@PathVariable Long id
	) {

		AuthorResponseDTO author = authorService.getAuthorById(id);

		return ResponseEntity.ok().body(author);
	}

	@PreAuthorize("hasAuthority('LIBRARIAN')")
	@PostMapping
	public ResponseEntity<AuthorResponseDTO> createAuthor(
			@Valid @RequestBody AuthorRequestDTO authorRequestDTO
	) {

		AuthorResponseDTO createdAuthor = authorService.createAuthor(authorRequestDTO);

		return ResponseEntity.ok().body(createdAuthor);
	}
}
