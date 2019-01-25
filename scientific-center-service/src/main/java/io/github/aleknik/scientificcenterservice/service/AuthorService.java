package io.github.aleknik.scientificcenterservice.service;

import io.github.aleknik.scientificcenterservice.model.domain.Author;
import io.github.aleknik.scientificcenterservice.repositroy.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final UserRepository userRepository;

    public AuthorService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Author createAuthor(Author author) {
        return userRepository.save(author);
    }
}
