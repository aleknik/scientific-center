package io.github.aleknik.scientificcenterservice.controller;

import io.github.aleknik.scientificcenterservice.model.domain.Issue;
import io.github.aleknik.scientificcenterservice.model.dto.IssueDto;
import io.github.aleknik.scientificcenterservice.model.dto.JournalDto;
import io.github.aleknik.scientificcenterservice.service.IssueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable long id) {
        final Issue issue = issueService.findById(id);

        final IssueDto issueDto = new IssueDto();
        issueDto.setId(issue.getId());
        issueDto.setMonth(issue.getMonth());
        issueDto.setYear(issue.getYear());

        final JournalDto journalDto = new JournalDto();

        journalDto.setOpenAccess(issue.getJournal().isOpenAccess());
        issueDto.setJournal(journalDto);

        return ResponseEntity.ok(issueDto);
    }

}
