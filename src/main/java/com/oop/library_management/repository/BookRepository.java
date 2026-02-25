package com.oop.library_management.repository;

import com.oop.library_management.model.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

	List<Book> findByAvailableCopiesGreaterThan(int availableCopies);

	List<Book> findAllByOrderByTitleAsc();

	@Query("""
						SELECT b FROM Book b ORDER BY b.title ASC LIMIT :bookLimit
			""")
	List<Book> findAllOrderByTitleLimit(@Param("bookLimit") int bookLimit);

	@Query("""
    SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END
    FROM Book b
    WHERE b.isbn IS NULL
    AND SIZE(b.authors) = :authorCount
    AND NOT EXISTS (
        SELECT 1 FROM Author a
        WHERE a MEMBER OF b.authors
        AND a.id NOT IN :authorIds
    )
    AND NOT EXISTS (
        SELECT 1 FROM Author a
        WHERE a.id IN :authorIds
        AND a NOT MEMBER OF b.authors
    )
    """)
	boolean existsNullIsbnWithExactAuthors(
			@Param("authorIds") Set<Long> authorIds,
			@Param("authorCount") Integer authorCount
	);

	boolean existsByIsbn(String isbn);
}
