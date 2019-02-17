package io.github.aleknik.scientificcenterservice.service.task;

import io.github.aleknik.scientificcenterservice.model.domain.User;
import io.github.aleknik.scientificcenterservice.service.MailService;
import io.github.aleknik.scientificcenterservice.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class PaperNotRelevant implements JavaDelegate {

    private final UserService userService;
    private final MailService mailService;

    public PaperNotRelevant(UserService userService, MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        final String authorUsername = (String) delegateExecution.getVariable("authorId");
        final User author = userService.findByUsername(authorUsername);

        final String message = (String) delegateExecution.getVariable("message");

        mailService.sendMail(author.getEmail(), "Paper is not relevant", message);

    }
}
