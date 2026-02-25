package com.oop.library_management.service;

import com.oop.library_management.dto.category.CategoryRequestDTO;
import com.oop.library_management.dto.category.CategoryResponseDTO;
import com.oop.library_management.exception.ResourceAlreadyExistsException;
import com.oop.library_management.exception.ResourceNotFoundException;
import com.oop.library_management.mapper.CategoryMapper;
import com.oop.library_management.model.category.Category;
import com.oop.library_management.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;

	public CategoryService(
			CategoryRepository categoryRepository,
			CategoryMapper categoryMapper
	) {
		this.categoryRepository = categoryRepository;
		this.categoryMapper = categoryMapper;
	}

	@Transactional(readOnly = true)
	public List<CategoryResponseDTO> searchCategoriesByName(
			String name
	) {

		if (name == null || name.isEmpty()) {
			return List.of();
		}

		return categoryRepository.findByNameContainingIgnoreCase(name).stream()
				.map(categoryMapper::toDTO)
				.toList();
	}

	@Transactional
	public CategoryResponseDTO createCategory(
			CategoryRequestDTO categoryRequestDTO
	) {

		if (
				categoryRepository
				.existsByNameIgnoreCase(categoryRequestDTO.getName())
		) {

			throw new ResourceAlreadyExistsException("Category already exists");
		}

		Category category = new Category(categoryRequestDTO.getName());
		Category savedCategory = categoryRepository.save(category);

		return categoryMapper.toDTO(savedCategory);
	}

	@Transactional(readOnly = true)
	public CategoryResponseDTO getCategoryById(Long id) {

		return categoryRepository.findById(id)
				.map(categoryMapper::toDTO)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found"));
	}
}
