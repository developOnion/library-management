package com.oop.library_management.controller;

import com.oop.library_management.dto.category.CategoryRequestDTO;
import com.oop.library_management.dto.category.CategoryResponseDTO;
import com.oop.library_management.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(
			CategoryService categoryService
	) {
		this.categoryService = categoryService;
	}

	@GetMapping
	public ResponseEntity<List<CategoryResponseDTO>> searchCategoriesByName(
			@RequestParam(required = false) String name
	) {

		List<CategoryResponseDTO> categories =
				categoryService.searchCategoriesByName(name);

		return ResponseEntity.ok().body(categories);
	}

	@PreAuthorize("hasAuthority('LIBRARIAN')")
	@GetMapping("/{id}")
	public ResponseEntity<CategoryResponseDTO> getCategoryById(
			@PathVariable Long id
	) {

		CategoryResponseDTO category =
				categoryService.getCategoryById(id);

		return ResponseEntity.ok().body(category);
	}

	@PreAuthorize("hasAuthority('LIBRARIAN')")
	@PostMapping
	public ResponseEntity<CategoryResponseDTO> createCategory(
			@Valid @RequestBody CategoryRequestDTO categoryRequestDTO
	) {

		CategoryResponseDTO createdCategory =
				categoryService.createCategory(categoryRequestDTO);

		return ResponseEntity.ok().body(createdCategory);
	}
}
