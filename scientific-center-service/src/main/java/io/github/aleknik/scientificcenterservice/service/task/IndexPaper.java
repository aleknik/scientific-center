package io.github.aleknik.scientificcenterservice.service.task;

import io.github.aleknik.scientificcenterservice.model.domain.Paper;
import io.github.aleknik.scientificcenterservice.model.domain.User;
import io.github.aleknik.scientificcenterservice.repository.PaperRepository;
import io.github.aleknik.scientificcenterservice.service.MailService;
import io.github.aleknik.scientificcenterservice.service.UserService;
import io.github.aleknik.scientificcenterservice.service.elasticsearch.PaperSearchService;
import io.github.aleknik.scientificcenterservice.util.DOIGenerator;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class IndexPaper implements JavaDelegate {

    private final PaperSearchService paperSearchService;
    private final UserService userService;
    private final MailService mailService;
    private final PaperRepository paperRepository;

    public IndexPaper(PaperSearchService paperSearchService, UserService userService, MailService mailService, PaperRepository paperRepository) {
        this.paperSearchService = paperSearchService;
        this.userService = userService;
        this.mailService = mailService;
        this.paperRepository = paperRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        final String paperId = (String) delegateExecution.getVariable("paperId");

        final Paper paper = paperRepository.findById(Long.valueOf(paperId)).get();

        paper.setDOI(DOIGenerator.generateForPaper(paper));
        paperRepository.save(paper);

        paperSearchService.indexPaper(Long.parseLong(paperId));

        final String authorId = (String) delegateExecution.getVariable("authorId");
        final User author = userService.findByUsername(authorId);

        mailService.sendMail(author.getEmail(), "Paper accepted", String.format("Dear %s, your paper '%s' has been accepted.", author.getUsername(), paper.getTitle()));
    }
}
