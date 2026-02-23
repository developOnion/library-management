package com.oop.library_management.mapper;

import com.oop.library_management.dto.category.CategoryResponseDTO;
import com.oop.library_management.model.book.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

	public CategoryResponseDTO toDTO(Category category) {

		if (category == null) {
			return null;
		}

		CategoryResponseDTO dto = new CategoryResponseDTO();
		dto.setId(category.getId());
		dto.setName(category.getName());

		return dto;
	}
}
