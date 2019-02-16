package io.github.aleknik.scientificcenterservice.service.task;

import io.github.aleknik.scientificcenterservice.model.domain.Journal;
import io.github.aleknik.scientificcenterservice.service.JournalService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class ChooseEditorByField implements JavaDelegate {

    private final JournalService journalService;

    public ChooseEditorByField(JournalService journalService) {
        this.journalService = journalService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        final String journalId = (String) delegateExecution.getVariable("journalId");
        final Journal journal = journalService.findById(Long.parseLong(journalId));


        delegateExecution.setVariable("chosenEditor", journal.getEditor().getUsername());

    }
}
