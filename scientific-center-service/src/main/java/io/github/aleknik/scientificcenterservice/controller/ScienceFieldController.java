package io.github.aleknik.scientificcenterservice.controller;

import io.github.aleknik.scientificcenterservice.service.ScienceFieldService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/science-fields")
public class ScienceFieldController {

    private final ScienceFieldService scienceFieldService;

    public ScienceFieldController(ScienceFieldService scienceFieldService) {
        this.scienceFieldService = scienceFieldService;
    }

    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.ok(scienceFieldService.findAll());
    }
}
