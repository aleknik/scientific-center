package io.github.aleknik.scientificcenterservice.service.task;

import io.github.aleknik.scientificcenterservice.model.domain.Editor;
import io.github.aleknik.scientificcenterservice.model.domain.Journal;
import io.github.aleknik.scientificcenterservice.model.domain.Paper;
import io.github.aleknik.scientificcenterservice.service.JournalService;
import io.github.aleknik.scientificcenterservice.service.MailService;
import io.github.aleknik.scientificcenterservice.service.PaperService;
import io.github.aleknik.scientificcenterservice.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class ChooseChiefEditor implements JavaDelegate {

    private final JournalService journalService;
    private final PaperService paperService;
    private final MailService mailService;
    private final UserService userService;

    public ChooseChiefEditor(JournalService journalService, PaperService paperService, MailService mailService, UserService userService) {
        this.journalService = journalService;
        this.paperService = paperService;
        this.mailService = mailService;
        this.userService = userService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        final String journalId = (String) delegateExecution.getVariable("journalId");
        final Journal journal = journalService.findById(Long.parseLong(journalId));

        final Editor editor = journal.getEditor();
        delegateExecution.setVariable("chiefEditorId", editor.getUsername());

        final String paperId = (String) delegateExecution.getVariable("paperId");
        final Paper paper = paperService.findById(Long.parseLong(paperId));

        paper.getAuthor().getEmail();

        mailService.sendMail(editor.getEmail(), "New paper submitted", String.format("Paper '%s' submitted", paper.getTitle()));
        mailService.sendMail(paper.getAuthor().getEmail(), "New paper submitted", String.format("Paper '%s' submitted", paper.getTitle()));
    }
}
