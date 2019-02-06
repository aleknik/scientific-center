package io.github.aleknik.scientificcenterservice.controller;

import io.github.aleknik.scientificcenterservice.model.domain.Journal;
import io.github.aleknik.scientificcenterservice.model.dto.JournalDto;
import io.github.aleknik.scientificcenterservice.service.JournalService;
import org.springframework.http.ResponseEntity;
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

    public JournalController(JournalService journalService) {
        this.journalService = journalService;
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

        return ResponseEntity.ok(journalDto);
    }
}
