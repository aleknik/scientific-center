package io.github.aleknik.scientificcenterservice.service.task;

import io.github.aleknik.scientificcenterservice.model.domain.Paper;
import io.github.aleknik.scientificcenterservice.model.domain.User;
import io.github.aleknik.scientificcenterservice.repository.PaperRepository;
import io.github.aleknik.scientificcenterservice.service.MailService;
import io.github.aleknik.scientificcenterservice.service.PaperService;
import io.github.aleknik.scientificcenterservice.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class ReviewerTimeout implements JavaDelegate {

    private final UserService userService;
    private final MailService mailService;
    private final PaperService paperService;

    private final PaperRepository paperRepository;

    public ReviewerTimeout(UserService userService, MailService mailService, PaperService paperService, PaperRepository paperRepository) {
        this.userService = userService;
        this.mailService = mailService;
        this.paperService = paperService;
        this.paperRepository = paperRepository;
    }


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        final String username = (String) delegateExecution.getVariable("chosenEditor");
        final User editor = userService.findByUsername(username);

        final String paperId = (String) delegateExecution.getVariable("paperId");
        final Paper paper = paperService.findById(Long.parseLong(paperId));

        final String reviewerUsername = (String) delegateExecution.getVariable("reviewer");

        paper.getReviewers().remove(userService.findByUsername(reviewerUsername));
        paperRepository.save(paper);

        mailService.sendMail(editor.getEmail(), "Reviewer '" + reviewerUsername + "' timed out", "Please select new reviewer");
    }
}
