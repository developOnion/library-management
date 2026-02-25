package com.oop.library_management.repository;

import com.oop.library_management.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	List<Category> findByNameContainingIgnoreCase(String name);

	boolean existsByNameIgnoreCase(String name);
}
