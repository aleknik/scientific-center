package io.github.aleknik.scientificcenterservice.service.task;

import io.github.aleknik.scientificcenterservice.model.domain.Journal;
import io.github.aleknik.scientificcenterservice.model.domain.User;
import io.github.aleknik.scientificcenterservice.model.dto.payment.PaymentStatus;
import io.github.aleknik.scientificcenterservice.service.JournalService;
import io.github.aleknik.scientificcenterservice.service.UserService;
import io.github.aleknik.scientificcenterservice.service.payment.PaymentService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class CheckSubscription implements JavaDelegate {

    private final JournalService journalService;
    private final PaymentService paymentService;
    private final UserService userService;

    public CheckSubscription(JournalService journalService, PaymentService paymentService, UserService userService) {
        this.journalService = journalService;
        this.paymentService = paymentService;
        this.userService = userService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        final String journalId = (String) delegateExecution.getVariable("journalId");
        final Journal journal = journalService.findById(Long.parseLong(journalId));

        final String authorUsername = (String) delegateExecution.getVariable("authorId");
        final User author = userService.findByUsername(authorUsername);

        incrementRetry(delegateExecution);
        PaymentStatus paymentStatus = null;
        try {
            paymentStatus = paymentService.journalStatus(journal.getId(), author);
        } catch (Exception e) {
            delegateExecution.setVariable("subscribed", false);
            return;
        }

        if (paymentStatus == PaymentStatus.SUCCESS) {
            delegateExecution.setVariable("subscribed", true);
        } else {
            delegateExecution.setVariable("subscribed", false);
        }
    }

    void incrementRetry(DelegateExecution delegateExecution) {
        final Integer retry = (Integer) delegateExecution.getVariable("retry");

        if (retry == null) {
            delegateExecution.setVariable("retry", 1);
        } else {
            delegateExecution.setVariable("retry", retry + 1);
        }
    }
}
