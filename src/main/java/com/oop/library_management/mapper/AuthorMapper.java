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

		AuthorResponseDTO dto = new AuthorResponseDTO();
		dto.setId(author.getId());
		dto.setFullName(author.getFullName());
		dto.setFirstName(author.getFirstName());
		dto.setLastName(author.getLastName());
		dto.setType(author.getType());

		return dto;
	}
}
