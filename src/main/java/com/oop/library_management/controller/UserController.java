package com.oop.library_management.controller;

import com.oop.library_management.dto.UserRequestDTO;
import com.oop.library_management.dto.UserResponseDTO;

import com.oop.library_management.dto.validationgroup.CreateLibrarianValidation;
import com.oop.library_management.service.UserService;
import jakarta.validation.groups.Default;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PreAuthorize("hasAuthority('LIBRARIAN')")
	@PostMapping("/members")
	public ResponseEntity<UserResponseDTO> registerMember(
			@Validated({Default.class})
			@RequestBody UserRequestDTO userDTO
	) {

		UserResponseDTO createdUser = userService.registerMember(userDTO);

		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	@PreAuthorize("hasAuthority('LIBRARIAN')")
	@PostMapping("/librarians")
	public ResponseEntity<UserResponseDTO> registerLibrarian(
			@Validated({Default.class, CreateLibrarianValidation.class})
			@RequestBody UserRequestDTO userDTO
	) {

		UserResponseDTO createdUser = userService.registerLibrarian(userDTO);

		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}
}
