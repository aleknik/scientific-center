package io.github.aleknik.scientificcenterservice.service.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.aleknik.scientificcenterservice.model.domain.Paper;
import io.github.aleknik.scientificcenterservice.model.domain.PaperReview;
import io.github.aleknik.scientificcenterservice.model.domain.Reviewer;
import io.github.aleknik.scientificcenterservice.model.dto.payment.UserDto;
import io.github.aleknik.scientificcenterservice.repository.PaperRepository;
import io.github.aleknik.scientificcenterservice.repository.UserRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class SavePaperReview implements JavaDelegate {

    private final ObjectMapper mapper;

    private final PaperRepository paperRepository;
    private final UserRepository userRepository;

    public SavePaperReview(ObjectMapper mapper, PaperRepository paperRepository, UserRepository userRepository) {
        this.mapper = mapper;
        this.paperRepository = paperRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        final String comments = (String) delegateExecution.getVariable("comments");
        final String suggestion = (String) delegateExecution.getVariable("suggestion");
        final String staffComments = (String) delegateExecution.getVariable("staffComments");

        final LinkedHashMap revDto = (LinkedHashMap) delegateExecution.getVariable("reviewer");
        final UserDto reviewerDto = mapper.convertValue(revDto, UserDto.class);

        final Reviewer reviewer = (Reviewer) userRepository.findById(reviewerDto.getId()).get();

        final PaperReview paperReview = new PaperReview(reviewer, comments, suggestion, staffComments);

        final String paperId = (String) delegateExecution.getVariable("paperId");

        final Paper paper = paperRepository.findById(Long.valueOf(paperId)).get();
        paper.getReviews().add(paperReview);

        paperRepository.save(paper);
    }
}
