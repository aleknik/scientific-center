package io.github.aleknik.scientificcenterservice.controller;

import io.github.aleknik.scientificcenterservice.model.dto.CreatePaperRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/papers")
public class PaperController {

    @PostMapping
    ResponseEntity createPaper(@ModelAttribute CreatePaperRequestDto createPaperRequestDto) {

        return ResponseEntity.ok(createPaperRequestDto.getTitle());
    }
}
