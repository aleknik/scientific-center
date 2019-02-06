package io.github.aleknik.scientificcenterservice.controller;

import io.github.aleknik.scientificcenterservice.model.domain.User;
import io.github.aleknik.scientificcenterservice.model.dto.auth.AuthenticationRequest;
import io.github.aleknik.scientificcenterservice.model.dto.auth.AuthenticationResponse;
import io.github.aleknik.scientificcenterservice.security.TokenUtils;
import io.github.aleknik.scientificcenterservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final UserService userService;

    private final TokenUtils tokenUtils;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UserService userService, TokenUtils tokenUtils) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.tokenUtils = tokenUtils;
    }

    @PostMapping
    public ResponseEntity authenticate(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final User user = userService.findByEmail(userDetails.getUsername());
        final String token = tokenUtils.generateToken(userDetails);
        return new ResponseEntity<>(new AuthenticationResponse(user.getId(), token), HttpStatus.OK);
    }
}
