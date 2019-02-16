package io.github.aleknik.scientificcenterservice.service.task;

import io.github.aleknik.scientificcenterservice.model.domain.Paper;
import io.github.aleknik.scientificcenterservice.repository.PaperRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class RejectPaper implements JavaDelegate {

    private final PaperRepository paperRepository;

    public RejectPaper(PaperRepository paperRepository) {
        this.paperRepository = paperRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        final String paperId = (String) delegateExecution.getVariable("paperId");

        if (paperId != null) {
            final Paper paper = paperRepository.findById(Long.valueOf(paperId)).get();
            paperRepository.delete(paper);
        }
    }
}
