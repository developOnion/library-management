package com.oop.library_management.service;

import com.oop.library_management.dto.auth.AuthRequestDTO;
import com.oop.library_management.dto.auth.AuthResponseDTO;
import com.oop.library_management.dto.user.UserRequestDTO;
import com.oop.library_management.dto.user.UserResponseDTO;
import com.oop.library_management.exception.AuthenticationException;
import com.oop.library_management.exception.ValidationException;
import com.oop.library_management.mapper.UserMapper;
import com.oop.library_management.model.user.Librarian;
import com.oop.library_management.model.user.Member;
import com.oop.library_management.model.user.Role;
import com.oop.library_management.model.user.User;
import com.oop.library_management.repository.UserRepository;
import com.oop.library_management.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final AuthenticationManager authManager;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;

	public UserService(
			UserRepository userRepository,
			UserMapper userMapper,
			AuthenticationManager authManager,
			JwtService jwtService,
			PasswordEncoder passwordEncoder
	) {

		this.userMapper = userMapper;
		this.userRepository = userRepository;
		this.authManager = authManager;
		this.jwtService = jwtService;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public UserResponseDTO registerMember(UserRequestDTO request) {

		validateUserRequest(request);

		User user = new Member(
				request.getUsername(),
				passwordEncoder.encode(request.getPassword()),
				request.getFirstName(),
				request.getLastName(),
				Role.MEMBER
		);

		User savedUser = userRepository.save(user);

		return userMapper.toDTO(savedUser);
	}

	@Transactional
	public UserResponseDTO registerLibrarian(UserRequestDTO userDTO) {

		validateLibrarianRequest(userDTO);

		User user = new Librarian(
				userDTO.getUsername(),
				passwordEncoder.encode(userDTO.getPassword()),
				userDTO.getFirstName(),
				userDTO.getLastName(),
				Role.LIBRARIAN,
				userDTO.getPosition()
		);

		User savedUser = userRepository.save(user);

		return userMapper.toDTO(savedUser);
	}

	@Transactional
	public AuthResponseDTO authenticate(AuthRequestDTO loginRequest) {

		try {
			// Authenticate user credentials
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					loginRequest.getUsername(),
					loginRequest.getPassword()
			);

			authManager.authenticate(authToken);

			// Fetch user from database to get the role
			User user = userRepository.findByUsername(loginRequest.getUsername())
					.orElseThrow(() -> new AuthenticationException("User not found"));

			// Update last login
			user.setLastLogin(java.time.LocalDateTime.now());
			userRepository.save(user);

			String jwtToken = jwtService.generateToken(
					user.getUsername(),
					user.getRole()
			);

			return new AuthResponseDTO(jwtToken);
		} catch (org.springframework.security.core.AuthenticationException e) {
			throw new AuthenticationException("Invalid username or password");
		}
	}

	private boolean isValidPassword(String password) {

		boolean hasNumber = password.matches(".*\\d.*");
		boolean hasCharacter = password.matches(".*[a-zA-Z].*");

		return hasNumber && hasCharacter;
	}

	private boolean isValidUsername(String username) {

		boolean hasNumber = username.matches(".*\\d.*");
		boolean hasOnlyLettersAndNumbers = username.matches("^[a-zA-Z0-9]+$");

		return hasOnlyLettersAndNumbers && hasNumber;
	}

	private void validateLibrarianRequest(UserRequestDTO userDTO) {

		validateUserRequest(userDTO);

		if (userDTO.getPosition() == null) {
			throw new ValidationException("Librarian position is required");
		}
	}

	private void validateUserRequest(UserRequestDTO userDTO) {

		if (userRepository.existsByUsername(userDTO.getUsername())) {
			throw new ValidationException("Username already exists");
		}

		if (!isValidUsername(userDTO.getUsername())) {
			throw new ValidationException(
					"Username must be 3-30 characters long and contain only letters and numbers");
		}

		if (!isValidPassword(userDTO.getPassword())) {
			throw new ValidationException(
					"Password must contain at least one number and one character");
		}
	}
}
