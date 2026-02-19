package com.oop.library_management.service;

import com.oop.library_management.dto.UserDTO;
import com.oop.library_management.mapper.UserMapper;
import com.oop.library_management.model.Member;
import com.oop.library_management.model.User;
import com.oop.library_management.repository.UserRepository;
import jakarta.validation.Valid;
import com.oop.library_management.exception.ValidationException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public UserService(
      UserRepository userRepository,
      UserMapper userMapper) {

    this.userMapper = userMapper;
    this.userRepository = userRepository;
  }

  public UserDTO register(@Valid UserDTO request) {

    if (userRepository.existsByUsername(request.getUsername())) {
      throw new ValidationException("Username already exists");
    }

    if (!isValidUsername(request.getUsername())) {
      throw new ValidationException(
          "Username must be 3-30 characters long and contain only letters and numbers");
    }

    if (!isValidPassword(request.getPassword())) {
      throw new ValidationException(
          "Password must contain at least one number and one character");
    }

    User user = new Member(
        request.getUsername(),
        request.getPassword(),
        request.getFirstName(),
        request.getLastName());

    userRepository.save(user);

    return userMapper.toDTO(user);
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
}
