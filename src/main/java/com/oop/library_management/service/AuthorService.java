package com.oop.library_management.service;

import com.oop.library_management.dto.author.AuthorRequestDTO;
import com.oop.library_management.dto.author.AuthorResponseDTO;
import com.oop.library_management.exception.ResourceNotFoundException;
import com.oop.library_management.mapper.AuthorMapper;
import com.oop.library_management.model.author.Author;
import com.oop.library_management.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorService {

	private final AuthorRepository authorRepository;
	private final AuthorMapper authorMapper;

	public AuthorService(
			AuthorRepository authorRepository,
			AuthorMapper authorMapper
	) {
		this.authorRepository = authorRepository;
		this.authorMapper = authorMapper;
	}

	@Transactional(readOnly = true)
	public List<AuthorResponseDTO> searchAuthorsByName(
			String name
	) {

		if (name == null || name.trim().isEmpty()) {
			return List.of();
		}

		return authorRepository.findByFullNameContainingIgnoreCase(name).stream()
				.map(authorMapper::toDTO)
				.toList();
	}

	@Transactional
	public AuthorResponseDTO createAuthor(
			AuthorRequestDTO authorRequestDTO
	) {

		Author author = new Author(
				authorRequestDTO.firstName(),
				authorRequestDTO.lastName(),
				authorRequestDTO.type()
		);

		Author savedAuthor = authorRepository.save(author);

		return authorMapper.toDTO(savedAuthor);
	}

	@Transactional(readOnly = true)
	public AuthorResponseDTO getAuthorById(Long id) {

		return authorRepository.findById(id)
				.map(authorMapper::toDTO)
				.orElseThrow(() -> new ResourceNotFoundException("Author not found"));
	}
}
