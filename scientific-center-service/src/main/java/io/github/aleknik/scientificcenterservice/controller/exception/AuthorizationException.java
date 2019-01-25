package io.github.aleknik.scientificcenterservice.controller.exception;


import io.github.aleknik.scientificcenterservice.controller.exception.resolver.ExceptionResolver;

/**
 * Custom exception.
 * Gets mapped to {@link org.springframework.http.HttpStatus#UNAUTHORIZED} when caught in
 * {@link ExceptionResolver}.
 */
public class AuthorizationException extends RuntimeException {

    public AuthorizationException() {
    }

    public AuthorizationException(String message) {
        super(message);
    }
}
