package io.github.aleknik.scientificcenterservice.service.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.aleknik.scientificcenterservice.model.domain.Paper;
import io.github.aleknik.scientificcenterservice.model.domain.User;
import io.github.aleknik.scientificcenterservice.model.dto.payment.UserDto;
import io.github.aleknik.scientificcenterservice.repository.PaperRepository;
import io.github.aleknik.scientificcenterservice.service.MailService;
import io.github.aleknik.scientificcenterservice.service.PaperService;
import io.github.aleknik.scientificcenterservice.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class ReviewerTimeout implements JavaDelegate {

    private final ObjectMapper mapper;

    private final UserService userService;
    private final MailService mailService;
    private final PaperService paperService;

    private final PaperRepository paperRepository;

    public ReviewerTimeout(ObjectMapper mapper, UserService userService, MailService mailService, PaperService paperService, PaperRepository paperRepository) {
        this.mapper = mapper;
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

        final LinkedHashMap revDto = (LinkedHashMap) delegateExecution.getVariable("reviewer");
        final UserDto reviewerDto = mapper.convertValue(revDto, UserDto.class);

        paper.getReviewers().remove(userService.findById(reviewerDto.getId()));
        paperRepository.save(paper);

        mailService.sendMail(editor.getEmail(), "Reviewer '" + reviewerDto.getUsername() + "' timed out", "Please select new reviewer");
    }
}
