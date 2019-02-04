package io.github.aleknik.scientificcenterservice.controller;

import io.github.aleknik.scientificcenterservice.model.domain.User;
import io.github.aleknik.scientificcenterservice.security.RoleConstants;
import io.github.aleknik.scientificcenterservice.service.PaymentService;
import io.github.aleknik.scientificcenterservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final UserService userService;
    private final PaymentService paymentService;

    public PaymentController(UserService userService, PaymentService paymentService) {
        this.userService = userService;
        this.paymentService = paymentService;
    }

    @GetMapping("/register-urls")
    @PreAuthorize("hasAuthority('" + RoleConstants.EDITOR + "')")
    public ResponseEntity getRegisterUrls() {
        final User currentUser = userService.findCurrentUser();

        return ResponseEntity.ok(paymentService.getRegisterMethods(currentUser));
    }
}
