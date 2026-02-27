package com.oop.library_management.repository;

import com.oop.library_management.model.author.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

	List<Author> findByFullNameContainingIgnoreCase(String fullName);

	Page<Author> findAllByFullNameContainingIgnoreCase(String fullName, Pageable pageable);

	Optional<Author> findByFullNameIgnoreCase(String fullName);
}
