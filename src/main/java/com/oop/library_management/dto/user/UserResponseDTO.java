package com.oop.library_management.dto.user;

import com.oop.library_management.model.user.Role;

public record UserResponseDTO(

		Long id,
		String username,
		String firstName,
		String lastName,
		Role role,
		String displayInfo
) {
}
