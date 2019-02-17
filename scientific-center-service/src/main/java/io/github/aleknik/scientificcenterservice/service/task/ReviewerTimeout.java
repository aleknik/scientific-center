package io.github.aleknik.scientificcenterservice.service.task;

import io.github.aleknik.scientificcenterservice.model.domain.User;
import io.github.aleknik.scientificcenterservice.service.MailService;
import io.github.aleknik.scientificcenterservice.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class ReviewerTimeout implements JavaDelegate {

    private final UserService userService;
    private final MailService mailService;

    public ReviewerTimeout(UserService userService, MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        final String username = (String) delegateExecution.getVariable("chosenEditor");
        final User editor = userService.findByUsername(username);

        mailService.sendMail(editor.getEmail(), "Reviewer timed out", "Please select new reviewer");
    }
}
