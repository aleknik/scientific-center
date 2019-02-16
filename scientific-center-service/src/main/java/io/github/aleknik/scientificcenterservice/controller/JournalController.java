package io.github.aleknik.scientificcenterservice.controller;

import io.github.aleknik.scientificcenterservice.model.domain.Journal;
import io.github.aleknik.scientificcenterservice.model.dto.IssueDto;
import io.github.aleknik.scientificcenterservice.model.dto.JournalDto;
import io.github.aleknik.scientificcenterservice.model.dto.TaskFormFieldDto;
import io.github.aleknik.scientificcenterservice.service.JournalService;
import io.github.aleknik.scientificcenterservice.service.UserService;
import io.github.aleknik.scientificcenterservice.service.process.ProcessService;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/journals")
public class JournalController {

    private final JournalService journalService;
    private final UserService userService;
    private final ProcessService processService;

    public JournalController(JournalService journalService, UserService userService, ProcessService processService) {
        this.journalService = journalService;
        this.userService = userService;
        this.processService = processService;
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

        return ResponseEntity.ok(createJournalDto(journal));
    }

    @PostMapping("/subscribe/{taskId}")
    public ResponseEntity subscribe(@PathVariable String taskId) {
        final ArrayList<TaskFormFieldDto> taskFormFieldDtos = new ArrayList<>();
        processService.submitTaskForm(taskId, taskFormFieldDtos);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("task/{taskId}")
    public ResponseEntity findByTaskId(@PathVariable String taskId) {
        final TaskDto task = processService.getTask(taskId);
        final String journalId = (String) processService.getVariable(task.getProcessInstanceId(), "journalId");

        final Journal journal = journalService.findById(Long.parseLong(journalId));

        return ResponseEntity.ok(createJournalDto(journal));
    }

    private JournalDto createJournalDto(Journal journal) {
        final JournalDto journalDto = new JournalDto();
        journalDto.setName(journal.getName());
        journalDto.setOpenAccess(journal.isOpenAccess());
        journalDto.setId(journal.getId());

        journalDto.setIssues(journal.getIssues().stream().map(issue ->
                new IssueDto(issue.getId(), issue.getYear(), issue.getMonth()))
                .collect(Collectors.toList()));

        return journalDto;
    }

    @PostMapping("/choose-journal/{taskId}")
    public ResponseEntity chooseJournal(@PathVariable String taskId, @RequestBody JournalDto journalDto) {
        final Journal journal = journalService.findById(journalDto.getId());
        final ArrayList<TaskFormFieldDto> taskFormFieldDtos = new ArrayList<>();
        taskFormFieldDtos.add(new TaskFormFieldDto("journalId", String.valueOf(journal.getId())));
        processService.submitTaskForm(taskId, taskFormFieldDtos);

        return ResponseEntity.ok().build();
    }
}
