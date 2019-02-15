package io.github.aleknik.scientificcenterservice.controller;

import io.github.aleknik.scientificcenterservice.model.domain.User;
import io.github.aleknik.scientificcenterservice.service.UserService;
import io.github.aleknik.scientificcenterservice.service.process.ProcessService;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final UserService userService;
    private final ProcessService processService;

    public TaskController(UserService userService, ProcessService processService) {
        this.userService = userService;
        this.processService = processService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    ResponseEntity getUserTasks() {
        final User currentUser = userService.findCurrentUser();
        final List<TaskDto> tasks = processService.getTasks(null, currentUser.getUsername());

        return ResponseEntity.ok(tasks);

    }
}
