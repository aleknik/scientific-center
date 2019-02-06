package io.github.aleknik.scientificcenterservice.controller;

import io.github.aleknik.scientificcenterservice.model.dto.FormFieldDto;
import io.github.aleknik.scientificcenterservice.model.dto.TaskFormDataDto;
import io.github.aleknik.scientificcenterservice.service.process.ProcessService;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.task.Task;
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
    public ResponseEntity register(@PathVariable String taskId, @RequestBody List<FormFieldDto> formFieldDtos) {
        processService.submmitTaskForm(taskId, formFieldDtos);

        return ResponseEntity.ok().build();
    }

    @GetMapping("form")
    public ResponseEntity getRegisterForm() {
        final String processId = processService.startProcess("registration");

        final List<Task> processTasks = processService.getProcessTasks(processId);
        final Task task = processTasks.stream().findFirst().get();
        final TaskFormData taskFormData = processService.getTaskFormData(task.getId());

        return ResponseEntity.ok(new TaskFormDataDto(task.getId(), taskFormData.getFormFields()));

    }
}
