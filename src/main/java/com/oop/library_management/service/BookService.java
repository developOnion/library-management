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
import com.oop.library_management.model.common.PageResponse;
import com.oop.library_management.repository.AuthorRepository;
import com.oop.library_management.repository.BookRepository;
import com.oop.library_management.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
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

	@Transactional(readOnly = true)
	public BookResponseDTO getBookById(Long id) {

		if (id == null || id <= 0) {
			throw new ValidationException("Invalid book ID");
		}

		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found"));

		return bookMapper.toDTO(book);
	}

	@Transactional(readOnly = true)
	public PageResponse<BookResponseDTO> findAllBooks(
			int page,
			int size
	) {

		Pageable pageable = PageRequest.of(
				page,
				size,
				Sort.by("createdAt").descending()
						.and(Sort.by("title").ascending())
		);

		Page<Book> books = bookRepository.findAll(pageable);
		List<BookResponseDTO> bookResponseDTOs = books.stream()
				.map(bookMapper::toDTO)
				.toList();

		return new PageResponse<>(
				bookResponseDTOs,
				books.getNumber(),
				books.getSize(),
				books.getTotalElements(),
				books.getTotalPages(),
				books.isFirst(),
				books.isLast()
		);
	}

	@Transactional
	public BookResponseDTO createBook(
			BookRequestDTO bookRequest
	) {

		if (
				bookRequest.isbn() != null &&
						bookRepository.existsByIsbn(bookRequest.isbn())
		) {
			throw new ResourceAlreadyExistsException("Book already exists");
		}

		if (
				bookRequest.isbn() == null &&
						bookRepository.existsNullIsbnWithExactAuthors(
								bookRequest.authorIds(),
								bookRequest.authorIds().size()
						)
		) {
			throw new ResourceAlreadyExistsException("Book already exists");
		}

		Book book = new Book(
				bookRequest.title(),
				bookRequest.totalCopies()
		);

		book.setIsbn(bookRequest.isbn());

		Set<Author> authors =
				new HashSet<>(
						authorRepository.findAllById(bookRequest.authorIds())
				);

		if (authors.size() != bookRequest.authorIds().size()) {
			throw new ResourceNotFoundException("One or more authors not found");
		}
		authors.forEach(book::addAuthor);

		Set<Category> categories =
				new HashSet<>(
						categoryRepository.findAllById(bookRequest.categoryIds())
				);

		if (categories.size() != bookRequest.categoryIds().size()) {
			throw new ResourceNotFoundException("One or more categories not found");
		}
		categories.forEach(book::addCategory);

		Book savedBook = bookRepository.save(book);

		return bookMapper.toDTO(savedBook);
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
