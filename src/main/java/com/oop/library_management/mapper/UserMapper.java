package com.oop.library_management.mapper;

import com.oop.library_management.dto.user.UserResponseDTO;
import com.oop.library_management.model.user.User;
import org.springframework.stereotype.Component;

/**
 * Mapper for {@link User} entities.
 * <p>
 * Extends {@link BaseMapper} to demonstrate <b>abstraction</b> through the
 * shared mapper contract. Calls {@code user.getDisplayInfo()} on a {@code User}
 * reference to demonstrate <b>polymorphism</b> — the JVM dispatches to the
 * correct {@code Member} or {@code Librarian} override at runtime.
 * </p>
 */
@Component
public class UserMapper extends BaseMapper<User, UserResponseDTO> {

  @Override
  public UserResponseDTO toDTO(User user) {

    if (user == null) {
      return null;
    }

    return new UserResponseDTO(
        user.getId(),
        user.getUsername(),
        user.getFirstName(),
        user.getLastName(),
        user.getRole(),
        user.getDisplayInfo()
    );
  }
}
