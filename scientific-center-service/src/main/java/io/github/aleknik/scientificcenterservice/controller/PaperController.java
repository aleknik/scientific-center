package io.github.aleknik.scientificcenterservice.controller;

import io.github.aleknik.scientificcenterservice.model.dto.CreatePaperRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("api/papers")
public class PaperController {

    @PostMapping
    @ResponseBody
    ResponseEntity createPaper(@RequestPart("data") @Valid CreatePaperRequestDto createPaperRequestDto,
                               @RequestPart("file") @Valid @NotNull @NotBlank MultipartFile file) {

        return ResponseEntity.ok(createPaperRequestDto.getTitle());
    }
}
