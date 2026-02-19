package com.oop.library_management.mapper;

import com.oop.library_management.dto.UserDTO;
import com.oop.library_management.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public UserDTO toDTO(User user) {

    if (user == null) {
      return null;
    }

    return new UserDTO(
        user.getId(),
        user.getUsername(),
        null,
        user.getFirstName(),
        user.getLastName());
  }
}
