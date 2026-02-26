package com.oop.library_management.mapper;

import com.oop.library_management.dto.category.CategoryResponseDTO;
import com.oop.library_management.model.category.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

	public CategoryResponseDTO toDTO(Category category) {

		if (category == null) {
			return null;
		}

		return new CategoryResponseDTO(
				category.getId(),
				category.getName()
		);
	}
}
