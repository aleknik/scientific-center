package io.github.aleknik.scientificcenterservice.controller;

import io.github.aleknik.scientificcenterservice.model.domain.Journal;
import io.github.aleknik.scientificcenterservice.model.dto.IssueDto;
import io.github.aleknik.scientificcenterservice.model.dto.JournalDto;
import io.github.aleknik.scientificcenterservice.security.RoleConstants;
import io.github.aleknik.scientificcenterservice.service.JournalService;
import io.github.aleknik.scientificcenterservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/journals")
public class JournalController {

    private final JournalService journalService;
    private final UserService userService;

    public JournalController(JournalService journalService, UserService userService) {
        this.journalService = journalService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity findAll() {
        final List<JournalDto> dtos = journalService.findAll().stream().map(journal -> {
            final JournalDto journalDto = new JournalDto();
            journalDto.setId(journal.getId());
            journalDto.setName(journal.getName());
            return journalDto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable long id) {
        final Journal journal = journalService.findById(id);

        final JournalDto journalDto = new JournalDto();
        journalDto.setName(journal.getName());
        journalDto.setOpenAccess(journal.isOpenAccess());
        journalDto.setId(journal.getId());

        journalDto.setIssues(journal.getIssues().stream().map(issue ->
                new IssueDto(issue.getId(), issue.getYear(), issue.getMonth()))
                .collect(Collectors.toList()));

        return ResponseEntity.ok(journalDto);
    }

    @GetMapping("/chose-journal/form")
    @PreAuthorize("hasAuthority('" + RoleConstants.AUTHOR + "')")
    public ResponseEntity getChoseJournalForm() {
        return null;
    }
}
