package com.oop.library_management.mapper;

import com.oop.library_management.dto.author.AuthorResponseDTO;
import com.oop.library_management.model.author.Author;
import org.springframework.stereotype.Component;

/**
 * Mapper component for converting between Author entities and DTOs.
 * <p>
 * This class handles the transformation of {@link Author} entities to
 * {@link AuthorResponseDTO} objects for API responses.
 * </p>
 *
 * @author Library Management System
 * @version 1.0
 * @since 2026-02-27
 */
@Component
public class AuthorMapper {

	/**
	 * Converts an Author entity to an AuthorResponseDTO.
	 * <p>
	 * Maps all relevant fields from the entity to the DTO for API consumption.
	 * Returns null if the input author is null.
	 * </p>
	 *
	 * @param author the author entity to convert
	 * @return the corresponding AuthorResponseDTO, or null if author is null
	 */
	public AuthorResponseDTO toDTO(Author author) {
		if (author == null) {
			return null;
		}

		return new AuthorResponseDTO(
				author.getId(),
				author.getFullName(),
				author.getFirstName(),
				author.getLastName(),
				author.getType()
		);
	}
}
