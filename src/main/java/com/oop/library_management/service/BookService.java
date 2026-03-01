package com.oop.library_management.service;

import com.oop.library_management.dto.book.BookRequestDTO;
import com.oop.library_management.dto.book.BookResponseDTO;
import com.oop.library_management.exception.ResourceAlreadyExistsException;
import com.oop.library_management.exception.ResourceNotFoundException;
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
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookService {

	private final BookRepository bookRepository;
	private final AuthorRepository authorRepository;
	private final CategoryRepository categoryRepository;
	private final BookMapper bookMapper;
	private final CorsConfigurationSource corsConfigurationSource;

	public BookService(
			BookRepository bookRepository,
			AuthorRepository authorRepository,
			CategoryRepository categoryRepository,
			BookMapper bookMapper,
			CorsConfigurationSource corsConfigurationSource) {

		this.bookRepository = bookRepository;
		this.authorRepository = authorRepository;
		this.categoryRepository = categoryRepository;
		this.bookMapper = bookMapper;
		this.corsConfigurationSource = corsConfigurationSource;
	}

	@Transactional(readOnly = true)
	public BookResponseDTO getBookById(Long id) {

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

		validateBookAlreadyExists(bookRequest);

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
	public BookResponseDTO updateBook(
			Long id,
			BookRequestDTO bookRequestDTO
	) {

		validateBookAlreadyExists(bookRequestDTO, id);

		Book existingBook = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found"));

		int borrowedCopies = existingBook.getTotalCopies() - existingBook.getAvailableCopies();
		if (bookRequestDTO.totalCopies() < borrowedCopies) {
			throw new IllegalArgumentException(
					"Total copies cannot be less than the number of borrowed copies"
			);
		}

		Set<Author> authors = new HashSet<>(
				authorRepository.findAllById(bookRequestDTO.authorIds())
		);

		if (authors.size() != bookRequestDTO.authorIds().size()) {
			throw new ResourceNotFoundException("One or more authors not found");
		}

		Set<Category> categories = new HashSet<>(
				categoryRepository.findAllById(bookRequestDTO.categoryIds())
		);

		if (categories.size() != bookRequestDTO.categoryIds().size()) {
			throw new ResourceNotFoundException("One or more categories not found");
		}

		existingBook.setTitle(bookRequestDTO.title());
		existingBook.setIsbn(bookRequestDTO.isbn());
		existingBook.setTotalCopies(bookRequestDTO.totalCopies());
		existingBook.setAvailableCopies(bookRequestDTO.totalCopies() - borrowedCopies);
		existingBook.setAuthors(authors);
		existingBook.setCategories(categories);

		Book updatedBook = bookRepository.save(existingBook);

		return bookMapper.toDTO(updatedBook);
	}

	@Transactional
	public void deleteBook(Long id) {

		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found"));

		bookRepository.delete(book);
	}

	private void validateBookAlreadyExists(BookRequestDTO bookRequestDTO) {

		if (
				bookRequestDTO.isbn() != null &&
						bookRepository.existsByIsbn(bookRequestDTO.isbn())
		) {
			throw new ResourceAlreadyExistsException("Book already exists");
		}

		if (
				bookRequestDTO.isbn() == null &&
						bookRepository.existsNullIsbnWithTitleAndExactAuthors(
								bookRequestDTO.title(),
								bookRequestDTO.authorIds(),
								bookRequestDTO.authorIds().size()
						)
		) {
			throw new ResourceAlreadyExistsException("Book already exists");
		}
	}

	private void validateBookAlreadyExists(
			BookRequestDTO bookRequestDTO,
			Long existingBookId
	) {

		if (
				bookRequestDTO.isbn() != null &&
						bookRepository.existsByIsbnAndIdNot(
								bookRequestDTO.isbn(),
								existingBookId
						)
		) {
			throw new ResourceAlreadyExistsException("Book already exists");
		}

		if (
				bookRequestDTO.isbn() == null &&
						bookRepository.existsNullIsbnWithTitleAndExactAuthorsAndIdNot(
								bookRequestDTO.title(),
								bookRequestDTO.authorIds(),
								bookRequestDTO.authorIds().size(),
								existingBookId
						)
		) {
			throw new ResourceAlreadyExistsException("Book already exists");
		}
	}
}
