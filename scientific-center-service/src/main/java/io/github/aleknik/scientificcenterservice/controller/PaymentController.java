package io.github.aleknik.scientificcenterservice.controller;

import io.github.aleknik.scientificcenterservice.model.domain.User;
import io.github.aleknik.scientificcenterservice.model.dto.payment.PaymentStatus;
import io.github.aleknik.scientificcenterservice.model.dto.payment.PurchaseRequest;
import io.github.aleknik.scientificcenterservice.security.RoleConstants;
import io.github.aleknik.scientificcenterservice.service.UserService;
import io.github.aleknik.scientificcenterservice.service.payment.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/buy-paper")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity buyPaper(@RequestBody PurchaseRequest purchaseRequest) {
        final User currentUser = userService.findCurrentUser();
        final String url = paymentService.buyPaper(purchaseRequest.getId(), currentUser,
                purchaseRequest.getSuccessUrl(),
                purchaseRequest.getErrorUrl());

        return ResponseEntity.ok(url);
    }

    @PostMapping("/subscribe-journal")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity subscribeToJournal(@RequestBody PurchaseRequest purchaseRequest) {
        final User currentUser = userService.findCurrentUser();
        final String url = paymentService.subscribeToJournal(purchaseRequest.getId(), currentUser,
                purchaseRequest.getSuccessUrl(),
                purchaseRequest.getErrorUrl());

        return ResponseEntity.ok(url);
    }

    @PostMapping("/buy-issue")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity buyIssue(@RequestBody PurchaseRequest purchaseRequest) {
        final User currentUser = userService.findCurrentUser();
        final String url = paymentService.buyIssue(purchaseRequest.getId(), currentUser,
                purchaseRequest.getSuccessUrl(),
                purchaseRequest.getErrorUrl());

        return ResponseEntity.ok(url);
    }


    @GetMapping("/status/papers/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity getPaperStatus(@PathVariable long id) {
        final User currentUser = userService.findCurrentUser();

        final PaymentStatus paymentStatus = paymentService.paperStatus(id, currentUser);

        return ResponseEntity.ok(paymentStatus.toString());
    }

    @GetMapping("/status/journals/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity getJournalStatus(@PathVariable long id) {
        final User currentUser = userService.findCurrentUser();

        final PaymentStatus paymentStatus = paymentService.journalStatus(id, currentUser);

        return ResponseEntity.ok(paymentStatus.toString());
    }

    @GetMapping("/status/issues/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity getIssueStatus(@PathVariable long id) {
        final User currentUser = userService.findCurrentUser();

        final PaymentStatus paymentStatus = paymentService.issueStatus(id, currentUser);

        return ResponseEntity.ok(paymentStatus.toString());
    }
}
