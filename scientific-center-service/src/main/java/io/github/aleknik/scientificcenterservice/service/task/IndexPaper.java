package io.github.aleknik.scientificcenterservice.service.task;

import io.github.aleknik.scientificcenterservice.service.PaperService;
import io.github.aleknik.scientificcenterservice.service.elasticsearch.PaperSearchService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class IndexPaper implements JavaDelegate {

    private final PaperService paperService;
    private final PaperSearchService paperSearchService;

    public IndexPaper(PaperService paperService, PaperSearchService paperSearchService) {
        this.paperService = paperService;
        this.paperSearchService = paperSearchService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        final String paperId = (String) delegateExecution.getVariable("paperId");

        paperSearchService.indexPaper(Long.parseLong(paperId));
    }
}
