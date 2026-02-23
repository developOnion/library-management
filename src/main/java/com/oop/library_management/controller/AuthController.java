package com.oop.library_management.controller;

import com.oop.library_management.dto.user.LoginRequestDTO;
import com.oop.library_management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final UserService userService;

	public AuthController(
			UserService userService
	) {

		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(
			@Valid @RequestBody LoginRequestDTO loginRequest
	) {

		String token = userService.verifyUserCredentials(loginRequest);

		return ResponseEntity.ok().body(token);
	}
}
