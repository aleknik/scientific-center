package io.github.aleknik.scientificcenterservice.service;

import io.github.aleknik.scientificcenterservice.controller.exception.BadRequestException;
import io.github.aleknik.scientificcenterservice.model.domain.Author;
import io.github.aleknik.scientificcenterservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final UserRepository userRepository;

    public AuthorService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Author createAuthor(Author author) {
        if (userRepository.findByUsername(author.getUsername()).isPresent()) {
            throw new BadRequestException("Username taken");
        }
        return userRepository.save(author);
    }
}
