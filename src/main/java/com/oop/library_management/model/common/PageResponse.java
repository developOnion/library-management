package com.oop.library_management.model.common;

import java.util.List;

public record PageResponse<T>(

		List<T> content,
		Integer number,
		Integer size,
		Long totalElements,
		Integer totalPages,
		Boolean first,
		Boolean last
) {
}
