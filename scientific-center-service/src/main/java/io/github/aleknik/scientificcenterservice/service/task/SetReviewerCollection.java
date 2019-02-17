package io.github.aleknik.scientificcenterservice.service.task;

import io.github.aleknik.scientificcenterservice.model.domain.Paper;
import io.github.aleknik.scientificcenterservice.repository.PaperRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetReviewerCollection implements JavaDelegate {

    private final PaperRepository paperRepository;

    public SetReviewerCollection(PaperRepository paperRepository) {
        this.paperRepository = paperRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        final String paperId = (String) delegateExecution.getVariable("paperId");

        final Paper paper = paperRepository.findById(Long.valueOf(paperId)).get();

        final List<String> reviewers = paper.getReviewers().stream()
                .map(r -> r.getUsername()).collect(Collectors.toList());


        delegateExecution.setVariable("reviewers", reviewers);
    }
}
