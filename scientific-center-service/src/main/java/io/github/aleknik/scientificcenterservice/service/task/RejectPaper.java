package io.github.aleknik.scientificcenterservice.service.task;

import io.github.aleknik.scientificcenterservice.model.domain.Paper;
import io.github.aleknik.scientificcenterservice.model.domain.User;
import io.github.aleknik.scientificcenterservice.repository.PaperRepository;
import io.github.aleknik.scientificcenterservice.service.MailService;
import io.github.aleknik.scientificcenterservice.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class RejectPaper implements JavaDelegate {

    private final PaperRepository paperRepository;
    private final MailService mailService;
    private final UserService userService;

    public RejectPaper(PaperRepository paperRepository, MailService mailService, UserService userService) {
        this.paperRepository = paperRepository;
        this.mailService = mailService;
        this.userService = userService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        final String paperId = (String) delegateExecution.getVariable("paperId");

        if (paperId != null) {
            final Paper paper = paperRepository.findById(Long.valueOf(paperId)).get();
            paperRepository.delete(paper);
        }

        final String authorId = (String) delegateExecution.getVariable("authorId");
        final User author = userService.findByUsername(authorId);

        mailService.sendMail(author.getEmail(), "Paper rejected", String.format("Dear %s, your paper has been rejected.", author.getUsername()));
    }
}
