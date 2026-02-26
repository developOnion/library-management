package com.oop.library_management.mapper;

import com.oop.library_management.dto.author.AuthorResponseDTO;
import com.oop.library_management.model.author.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

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
