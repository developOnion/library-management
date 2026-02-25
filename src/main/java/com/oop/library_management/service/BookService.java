package com.oop.library_management.service;

import com.oop.library_management.dto.book.BookRequestDTO;
import com.oop.library_management.dto.book.BookResponseDTO;
import com.oop.library_management.exception.ResourceAlreadyExistsException;
import com.oop.library_management.exception.ResourceNotFoundException;
import com.oop.library_management.exception.ValidationException;
import com.oop.library_management.mapper.BookMapper;
import com.oop.library_management.model.author.Author;
import com.oop.library_management.model.book.Book;
import com.oop.library_management.model.category.Category;
import com.oop.library_management.repository.AuthorRepository;
import com.oop.library_management.repository.BookRepository;
import com.oop.library_management.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class BookService {

	private final BookRepository bookRepository;
	private final AuthorRepository authorRepository;
	private final CategoryRepository categoryRepository;
	private final BookMapper bookMapper;

	public BookService(
			BookRepository bookRepository,
			AuthorRepository authorRepository,
			CategoryRepository categoryRepository,
			BookMapper bookMapper
	) {

		this.bookRepository = bookRepository;
		this.authorRepository = authorRepository;
		this.categoryRepository = categoryRepository;
		this.bookMapper = bookMapper;
	}

	@Transactional
	public BookResponseDTO createBook(
			BookRequestDTO bookRequest
	) {

		if (
				bookRequest.getIsbn() != null &&
				bookRepository.existsByIsbn(bookRequest.getIsbn())
		) {
			throw new ResourceAlreadyExistsException("Book already exists");
		}

		if (
				bookRequest.getIsbn() == null &&
				bookRepository.existsNullIsbnWithExactAuthors(
						bookRequest.getAuthorIds(),
						bookRequest.getAuthorIds().size()
				)
		) {
			throw new ResourceAlreadyExistsException("Book already exists");
		}

		Book book = new Book(
				bookRequest.getTitle(),
				bookRequest.getTotalCopies()
		);

		book.setIsbn(bookRequest.getIsbn());

		Set<Author> authors =
				new HashSet<>(
						authorRepository.findAllById(bookRequest.getAuthorIds())
				);

		if (authors.size() != bookRequest.getAuthorIds().size()) {
			throw new ResourceNotFoundException("One or more authors not found");
		}
		authors.forEach(book::addAuthor);

		Set<Category> categories =
				new HashSet<>(
						categoryRepository.findAllById(bookRequest.getCategoryIds())
				);

		if (categories.size() != bookRequest.getCategoryIds().size()) {
			throw new ResourceNotFoundException("One or more categories not found");
		}
		categories.forEach(book::addCategory);

		Book savedBook = bookRepository.save(book);

		return bookMapper.toDTO(savedBook);
	}

	@Transactional(readOnly = true)
	public BookResponseDTO getBookById(Long id) {

		if (id == null || id <= 0) {
			throw new ValidationException("Invalid book ID");
		}

		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found"));

		return bookMapper.toDTO(book);
	}

	@Transactional
	public void deleteBook(Long id) {

		if (id == null || id <= 0) {
			throw new ValidationException("Invalid book ID");
		}

		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found"));

		bookRepository.delete(book);
	}
}
