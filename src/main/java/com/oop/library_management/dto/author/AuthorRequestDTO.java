package com.oop.library_management.dto.author;

import com.oop.library_management.model.author.AuthorType;
import jakarta.validation.constraints.*;

public record AuthorRequestDTO(

		@Size(max = 50, message = "First name must be at most 50 characters")
		@Pattern(regexp = "^\\S+$", message = "First name cannot be blank or contain whitespace")
		String firstName,

		@Size(max = 50, message = "Last name must be at most 50 characters")
		@Pattern(regexp = "^\\S+$", message = "Last name cannot be blank or contain whitespace")
		String lastName,

		@NotNull(message = "Author type is required")
		AuthorType type
) {
}
