package com.oop.library_management.mapper;

import com.oop.library_management.dto.user.UserResponseDTO;
import com.oop.library_management.model.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public UserResponseDTO toDTO(User user) {

    if (user == null) {
      return null;
    }

    return new UserResponseDTO(
        user.getId(),
        user.getUsername(),
        user.getFirstName(),
        user.getLastName(),
				user.getRole()
		);
  }
}
