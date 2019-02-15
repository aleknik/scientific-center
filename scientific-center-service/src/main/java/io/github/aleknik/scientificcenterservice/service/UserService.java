package io.github.aleknik.scientificcenterservice.service;

import io.github.aleknik.scientificcenterservice.controller.exception.BadRequestException;
import io.github.aleknik.scientificcenterservice.controller.exception.ForbiddenException;
import io.github.aleknik.scientificcenterservice.controller.exception.NotFoundException;
import io.github.aleknik.scientificcenterservice.model.domain.Role;
import io.github.aleknik.scientificcenterservice.model.domain.User;
import io.github.aleknik.scientificcenterservice.repository.RoleRepository;
import io.github.aleknik.scientificcenterservice.repository.UserRepository;
import io.github.aleknik.scientificcenterservice.service.payment.PaymentService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PaymentService paymentService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PaymentService paymentService, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.paymentService = paymentService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User findCurrentUser() {
        final UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ForbiddenException("User is not authenticated!");
        }

        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return findByUsername(userDetails.getUsername());
    }

    public User createUser(User user, String roleName) {
        final Role role = roleRepository.findByName(roleName).orElseThrow(() -> new BadRequestException("Role not found"));

        final List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new BadRequestException("Username taken");
        }
        user = userRepository.save(user);
        try {
            return paymentService.register(user);
        } catch (Exception ignored) {
        }

        return user;
    }
}
