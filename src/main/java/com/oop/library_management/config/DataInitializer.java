package com.oop.library_management.config;

import com.oop.library_management.model.author.Author;
import com.oop.library_management.model.author.AuthorType;
import com.oop.library_management.model.user.Librarian;
import com.oop.library_management.model.user.LibrarianPosition;
import com.oop.library_management.model.user.Role;
import com.oop.library_management.model.user.User;
import com.oop.library_management.repository.AuthorRepository;
import com.oop.library_management.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

	private final UserRepository userRepository;
	private final AuthorRepository authorRepository;
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

	public DataInitializer(
			UserRepository userRepository,
			AuthorRepository authorRepository
	) {

		this.userRepository = userRepository;
		this.authorRepository = authorRepository;
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

		if (authorRepository.findByFullNameIgnoreCase("Unknown Author").isEmpty()) {

			Author unknownAuthor = new Author(
					"Unknown",
					"Author",
					AuthorType.INDIVIDUAL
			);

			authorRepository.save(unknownAuthor);
		}
	}
}
