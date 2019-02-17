package io.github.aleknik.scientificcenterservice.service.task;

import io.github.aleknik.scientificcenterservice.model.domain.Editor;
import io.github.aleknik.scientificcenterservice.model.domain.Journal;
import io.github.aleknik.scientificcenterservice.model.domain.JournalEditor;
import io.github.aleknik.scientificcenterservice.model.domain.Paper;
import io.github.aleknik.scientificcenterservice.service.JournalService;
import io.github.aleknik.scientificcenterservice.service.MailService;
import io.github.aleknik.scientificcenterservice.service.PaperService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChooseEditorByField implements JavaDelegate {

    private final JournalService journalService;
    private final PaperService paperService;
    private final MailService mailService;

    public ChooseEditorByField(JournalService journalService, PaperService paperService, MailService mailService) {
        this.journalService = journalService;
        this.paperService = paperService;
        this.mailService = mailService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        final String journalId = (String) delegateExecution.getVariable("journalId");
        final Journal journal = journalService.findById(Long.parseLong(journalId));

        final String paperId = (String) delegateExecution.getVariable("paperId");
        final Paper paper = paperService.findById(Long.parseLong(paperId));

        final Optional<JournalEditor> journalEditor = journal.getJournalEditors()
                .stream()
                .filter(je -> je.getScienceField().getId() == paper.getScienceField().getId()).findFirst();
        Editor editor;
        if (journalEditor.isPresent()) {
            editor = journalEditor.get().getEditor();
        } else {
            editor = journal.getEditor();
        }

        delegateExecution.setVariable("chosenEditor", editor.getUsername());

        mailService.sendMail(editor.getEmail(), "Paper assigned", String.format("You have been assigned a paper '%s'", paper.getTitle()));

    }
}
