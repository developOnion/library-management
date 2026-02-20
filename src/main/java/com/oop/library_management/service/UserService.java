package com.oop.library_management.service;

import com.oop.library_management.dto.UserRequestDTO;
import com.oop.library_management.dto.UserResponseDTO;
import com.oop.library_management.exception.ValidationException;
import com.oop.library_management.mapper.UserMapper;
import com.oop.library_management.model.Librarian;
import com.oop.library_management.model.Member;
import com.oop.library_management.model.Role;
import com.oop.library_management.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

	public UserService(
			UserRepository userRepository,
			UserMapper userMapper
	) {

		this.userMapper = userMapper;
		this.userRepository = userRepository;
	}

	@Transactional
	public UserResponseDTO registerMember(UserRequestDTO request) {

		validateUserRequest(request);

		Member member = new Member(
				request.getUsername(),
				passwordEncoder.encode(request.getPassword()),
				request.getFirstName(),
				request.getLastName(),
				Role.MEMBER
		);

		Member savedMember = userRepository.save(member);

		return userMapper.toDTO(savedMember);
	}

	public UserResponseDTO registerLibrarian(UserRequestDTO userDTO) {

		validateLibrarianRequest(userDTO);

		Librarian librarian = new Librarian(
				userDTO.getUsername(),
				passwordEncoder.encode(userDTO.getPassword()),
				userDTO.getFirstName(),
				userDTO.getLastName(),
				Role.LIBRARIAN,
				userDTO.getPosition()
		);

		Librarian savedLibrarian = userRepository.save(librarian);

		return userMapper.toDTO(savedLibrarian);
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
