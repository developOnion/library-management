package com.oop.library_management.config;

import com.oop.library_management.model.Librarian;
import com.oop.library_management.model.LibrarianPosition;
import com.oop.library_management.model.Role;
import com.oop.library_management.model.User;
import com.oop.library_management.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

	public DataInitializer(
			UserRepository userRepository
	) {
		this.userRepository = userRepository;
	}

	@Override
	public void run(String... args) throws Exception {

		if (userRepository.findByUsername("onion").isEmpty()) {

			User user = new Librarian(
					"onion",
					passwordEncoder.encode("onionring"),
					"Default",
					"Librarian",
					Role.LIBRARIAN,
					LibrarianPosition.HEAD_LIBRARIAN
			);

			userRepository.save(user);
		}
	}
}
