package com.oop.library_management.mapper;

import com.oop.library_management.dto.book.BookResponseDTO;
import com.oop.library_management.model.book.Book;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BookMapper {

	private final AuthorMapper authorMapper;
	private final CategoryMapper categoryMapper;

	public BookMapper(
			AuthorMapper authorMapper,
			CategoryMapper categoryMapper
	) {

		this.authorMapper = authorMapper;
		this.categoryMapper = categoryMapper;
	}

	public BookResponseDTO toDTO(Book book) {

		if (book == null) {
			return null;
		}

		return new BookResponseDTO(
				book.getId(),
				book.getTitle(),
				book.getIsbn(),
				book.getTotalCopies(),
				book.getAvailableCopies(),
				book.getAuthors().stream()
						.map(authorMapper::toDTO)
						.toList(),
				book.getCategories().stream()
						.map(categoryMapper::toDTO)
						.toList()
		);
	}
}
