package io.github.aleknik.scientificcenterservice.controller;

import io.github.aleknik.scientificcenterservice.model.dto.TaskFormDataDto;
import io.github.aleknik.scientificcenterservice.model.dto.TaskFormFieldDto;
import io.github.aleknik.scientificcenterservice.service.process.ProcessService;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/authors")
public class AuthorController {

    private final ProcessService processService;

    public AuthorController(ProcessService processService) {
        this.processService = processService;
    }

    @PostMapping("{taskId}")
    public ResponseEntity register(@PathVariable String taskId, @RequestBody List<TaskFormFieldDto> formFieldDtos) {
        processService.submitTaskForm(taskId, formFieldDtos);

        return ResponseEntity.ok().build();
    }

    @GetMapping("form")
    public ResponseEntity getRegisterForm() {
        final String processId = processService.startProcess("registration", null);

        final List<TaskDto> processTasks = processService.getProcessTasks(processId);
        final TaskDto task = processTasks.stream().findFirst().get();
        final TaskFormDataDto taskFormData = processService.getTaskFormData(task.getId());

        return ResponseEntity.ok(taskFormData);

    }
}
