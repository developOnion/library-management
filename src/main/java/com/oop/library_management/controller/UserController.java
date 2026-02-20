package com.oop.library_management.controller;

import com.oop.library_management.dto.UserRequestDTO;
import com.oop.library_management.dto.UserResponseDTO;

import com.oop.library_management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
	@ResponseStatus(HttpStatus.CREATED)
	public UserResponseDTO registerMember(@Valid @RequestBody UserRequestDTO userDTO) {
		return userService.registerMember(userDTO);
	}

	@PreAuthorize("hasAuthority('LIBRARIAN')")
	@PostMapping("/librarians")
	@ResponseStatus(HttpStatus.CREATED)
	public UserResponseDTO registerLibrarian(@Valid @RequestBody UserRequestDTO userDTO) {
		return userService.registerLibrarian(userDTO);
	}
}
