package com.oop.library_management.dto.author;

import com.oop.library_management.model.author.AuthorType;

public record AuthorResponseDTO(
		Long id,
		String fullName,
		String firstName,
		String lastName,
		AuthorType type
) {
}
