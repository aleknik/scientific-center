package io.github.aleknik.scientificcenterservice.service.task;

import io.github.aleknik.scientificcenterservice.model.domain.Journal;
import io.github.aleknik.scientificcenterservice.service.JournalService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class CheckOpenAccess implements JavaDelegate {

    private final JournalService journalService;

    public CheckOpenAccess(JournalService journalService) {
        this.journalService = journalService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String journalId = (String) delegateExecution.getVariable("journalId");

        final Journal journal = journalService.findById(Long.parseLong(journalId));

        delegateExecution.setVariable("journalOpenAccess", journal.isOpenAccess());

    }
}
