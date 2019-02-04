package io.github.aleknik.scientificcenterservice.service;

import io.github.aleknik.scientificcenterservice.controller.exception.ForbiddenException;
import io.github.aleknik.scientificcenterservice.controller.exception.NotFoundException;
import io.github.aleknik.scientificcenterservice.model.domain.User;
import io.github.aleknik.scientificcenterservice.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PaymentService paymentService;

    public UserService(UserRepository userRepository, PaymentService paymentService) {
        this.userRepository = userRepository;
        this.paymentService = paymentService;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User findCurrentUser() {
        final UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ForbiddenException("User is not authenticated!");
        }

        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return findByEmail(userDetails.getUsername());
    }

    public User createUser(User user) {
        user = userRepository.save(user);
        return paymentService.register(user);
    }
}
