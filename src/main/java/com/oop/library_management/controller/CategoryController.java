package com.oop.library_management.controller;

import com.oop.library_management.dto.category.CategoryRequestDTO;
import com.oop.library_management.dto.category.CategoryResponseDTO;
import com.oop.library_management.model.common.PageResponse;
import com.oop.library_management.service.CategoryService;
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
 * REST controller for managing book categories in the library system.
 * <p>
 * Provides endpoints for searching, retrieving, and creating categories.
 * Categories are used to organize and classify books in the library.
 * </p>
 *
 * @author Nou Reaksmey, Ngov Lysovath
 */
@RestController
@RequestMapping("/categories")
@Tag(name = "Category Management", description = "Endpoints for managing book categories in the library")
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(
			CategoryService categoryService
	) {
		this.categoryService = categoryService;
	}

	/**
	 * Searches for categories by name with pagination support.
	 * <p>
	 * Performs a case-insensitive partial match on category names.
	 * If no name parameter is provided, returns all categories.
	 * Results are paginated according to the specified page and size parameters.
	 * </p>
	 *
	 * @param page the zero-based page number (default: 0)
	 * @param size the number of items per page (default: 10)
	 * @param name the search term for category name (optional, partial match)
	 * @return paginated response containing matching categories
	 */
	@Operation(
			summary = "Search categories by name",
			description = "Returns a paginated list of categories matching the search criteria. " +
					"Performs case-insensitive partial matching on category names. " +
					"Returns all categories if name parameter is not provided."
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Successfully retrieved categories",
					content = @Content(schema = @Schema(implementation = PageResponse.class))
			)
	})
	@GetMapping
	public ResponseEntity<PageResponse<CategoryResponseDTO>> searchCategoriesByName(
			@Parameter(description = "Page number (0-indexed)", example = "0")
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@Parameter(description = "Number of items per page", example = "10")
			@RequestParam(name = "size", defaultValue = "10", required = false) int size,
			@Parameter(description = "Category name to search for (partial match, case-insensitive)", example = "Fiction")
			@RequestParam(required = false) String name
	) {

		PageResponse<CategoryResponseDTO> categories =
				categoryService.searchCategoriesByName(page, size, name);

		return ResponseEntity.ok().body(categories);
	}

	/**
	 * Retrieves a specific category by its unique identifier.
	 * <p>
	 * Requires LIBRARIAN authority to access.
	 * </p>
	 *
	 * @param id the unique identifier of the category
	 * @return the category details
	 * @throws com.oop.library_management.exception.ResourceNotFoundException if category not found
	 */
	@Operation(
			summary = "Get category by ID",
			description = "Retrieves detailed information about a specific category. Requires LIBRARIAN role."
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Successfully retrieved category",
					content = @Content(schema = @Schema(implementation = CategoryResponseDTO.class))
			),
			@ApiResponse(
					responseCode = "404",
					description = "Category not found"
			),
			@ApiResponse(
					responseCode = "403",
					description = "Access denied - LIBRARIAN role required"
			)
	})
	@PreAuthorize("hasAuthority('LIBRARIAN')")
	@GetMapping("/{id}")
	public ResponseEntity<CategoryResponseDTO> getCategoryById(
			@Parameter(description = "Category ID", example = "1", required = true)
			@PathVariable Long id
	) {

		CategoryResponseDTO category =
				categoryService.getCategoryById(id);

		return ResponseEntity.ok().body(category);
	}

	/**
	 * Creates a new category in the system.
	 * <p>
	 * Requires LIBRARIAN authority to access.
	 * Validates the input data and persists the new category.
	 * </p>
	 *
	 * @param categoryRequestDTO the category data to create
	 * @return the created category with generated ID
	 */
	@Operation(
			summary = "Create a new category",
			description = "Creates a new category in the library system. Requires LIBRARIAN role."
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Category successfully created",
					content = @Content(schema = @Schema(implementation = CategoryResponseDTO.class))
			),
			@ApiResponse(
					responseCode = "400",
					description = "Invalid input data"
			),
			@ApiResponse(
					responseCode = "403",
					description = "Access denied - LIBRARIAN role required"
			),
			@ApiResponse(
					responseCode = "409",
					description = "Category with the same name already exists"
			)
	})
	@PreAuthorize("hasAuthority('LIBRARIAN')")
	@PostMapping
	public ResponseEntity<CategoryResponseDTO> createCategory(
			@Parameter(description = "Category data to create", required = true)
			@Valid @RequestBody CategoryRequestDTO categoryRequestDTO
	) {

		CategoryResponseDTO createdCategory =
				categoryService.createCategory(categoryRequestDTO);

		return ResponseEntity.ok().body(createdCategory);
	}
}
