package com.oop.library_management.dto.book;

import com.oop.library_management.dto.author.AuthorResponseDTO;
import com.oop.library_management.dto.category.CategoryResponseDTO;

import java.util.ArrayList;
import java.util.List;

public record BookResponseDTO(
		
		Long id,
		String title,
		String isbn,
		int totalCopies,
		int availableCopies,
		List<AuthorResponseDTO> authors,
		List<CategoryResponseDTO> categories
) {
}
