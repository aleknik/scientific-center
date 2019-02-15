package io.github.aleknik.scientificcenterservice.service.task;

import io.github.aleknik.scientificcenterservice.model.domain.Journal;
import io.github.aleknik.scientificcenterservice.service.JournalService;
import io.github.aleknik.scientificcenterservice.service.PaperService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class ChoseChiefEditor implements JavaDelegate {

    private final PaperService paperService;
    private final JournalService journalService;

    public ChoseChiefEditor(PaperService paperService, JournalService journalService) {
        this.paperService = paperService;
        this.journalService = journalService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        final String journalId = (String) delegateExecution.getVariable("journalId");

        final Journal journal = journalService.findById(Long.parseLong(journalId));

        delegateExecution.setVariable("chiefEditorId", journal.getEditor().getUsername());
    }
}
