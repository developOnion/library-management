package com.oop.library_management.repository;

import com.oop.library_management.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	List<Book> findByTitleContainingIgnoreCase(String title);

	@Query("""
					SELECT DISTINCT b FROM Book b
					JOIN b.authors a
					WHERE LOWER(CONCAT(a.firstName, " ", a.lastName))
								LIKE LOWER(CONCAT("%", :fullName, "%"))
				""")
	List<Book> findByAuthorFullName(@Param("fullName") String fullName);

	List<Book> findByCategories_NameIgnoreCase(String categoryName);

	List<Book> findByTitleContainingIgnoreCaseAndAvailableCopiesGreaterThan(String title, int availableCopies);

	Optional<Book> findByIsbn(String isbn);
}
