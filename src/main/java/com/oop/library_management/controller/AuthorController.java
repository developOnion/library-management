package com.oop.library_management.controller;

import com.oop.library_management.dto.author.AuthorRequestDTO;
import com.oop.library_management.dto.author.AuthorResponseDTO;
import com.oop.library_management.model.common.PageResponse;
import com.oop.library_management.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing authors in the library system.
 * <p>
 * Provides endpoints for searching, retrieving, and creating authors.
 * Authors can be individuals or corporate entities.
 * </p>
 *
 * @author Nou Reaksmey, Ngov Lysovath
 */
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

	/**
	 * Searches for authors by name with pagination support.
	 * <p>
	 * Performs a case-insensitive partial match on author full names.
	 * Results are sorted by last name, then first name in ascending order.
	 * Returns empty results if name parameter is null or blank.
	 * </p>
	 *
	 * @param page the zero-based page number (default: 0)
	 * @param size the number of items per page (default: 10)
	 * @param name the search term for author name (optional, partial match)
	 * @return paginated response containing matching authors
	 */
	@Operation(
			summary = "Search authors by name",
			description = "Returns a paginated list of authors matching the search criteria. " +
					"Performs case-insensitive partial matching on author full names. " +
					"Returns empty results if name is not provided."
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Successfully retrieved authors",
					content = @Content(schema = @Schema(implementation = PageResponse.class))
			)
	})
	@GetMapping
	public ResponseEntity<PageResponse<AuthorResponseDTO>> searchAuthorsByName(
			@Parameter(description = "Page number (0-indexed)", example = "0")
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@Parameter(description = "Number of items per page", example = "10")
			@RequestParam(name = "size", defaultValue = "10", required = false) int size,
			@Parameter(description = "Author name to search for (partial match, case-insensitive)", example = "Smith")
			@RequestParam(name = "name", required = false) String name
	) {

		PageResponse<AuthorResponseDTO> authors = authorService.searchAuthorsByName(name, page, size);

		return ResponseEntity.ok().body(authors);
	}

	/**
	 * Retrieves a specific author by their unique identifier.
	 * <p>
	 * Requires LIBRARIAN authority to access.
	 * </p>
	 *
	 * @param id the unique identifier of the author
	 * @return the author details
	 * @throws com.oop.library_management.exception.ResourceNotFoundException if author not found
	 */
	@Operation(
			summary = "Get author by ID",
			description = "Retrieves detailed information about a specific author. Requires LIBRARIAN role."
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Successfully retrieved author",
					content = @Content(schema = @Schema(implementation = AuthorResponseDTO.class))
			),
			@ApiResponse(
					responseCode = "404",
					description = "Author not found"
			),
			@ApiResponse(
					responseCode = "403",
					description = "Access denied - LIBRARIAN role required"
			)
	})
	@PreAuthorize("hasAuthority('LIBRARIAN')")
	@GetMapping("/{id}")
	public ResponseEntity<AuthorResponseDTO> getAuthorById(
			@Parameter(description = "Author ID", example = "1", required = true)
			@PathVariable Long id
	) {

		AuthorResponseDTO author = authorService.getAuthorById(id);

		return ResponseEntity.ok().body(author);
	}

	/**
	 * Creates a new author in the system.
	 * <p>
	 * Requires LIBRARIAN authority to access.
	 * Validates the input data and persists the new author.
	 * </p>
	 *
	 * @param authorRequestDTO the author data to create
	 * @return the created author with generated ID
	 */
	@Operation(
			summary = "Create a new author",
			description = "Creates a new author in the library system. Requires LIBRARIAN role. " +
					"Author type can be INDIVIDUAL or CORPORATE."
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Author successfully created",
					content = @Content(schema = @Schema(implementation = AuthorResponseDTO.class))
			),
			@ApiResponse(
					responseCode = "400",
					description = "Invalid input data"
			),
			@ApiResponse(
					responseCode = "403",
					description = "Access denied - LIBRARIAN role required"
			)
	})
	@PreAuthorize("hasAuthority('LIBRARIAN')")
	@PostMapping
	public ResponseEntity<AuthorResponseDTO> createAuthor(
			@Parameter(description = "Author data to create", required = true)
			@Valid @RequestBody AuthorRequestDTO authorRequestDTO
	) {

		AuthorResponseDTO createdAuthor = authorService.createAuthor(authorRequestDTO);

		return ResponseEntity.ok().body(createdAuthor);
	}
}
