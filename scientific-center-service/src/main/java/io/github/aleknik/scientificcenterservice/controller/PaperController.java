package io.github.aleknik.scientificcenterservice.controller;

import io.github.aleknik.scientificcenterservice.model.domain.Address;
import io.github.aleknik.scientificcenterservice.model.domain.Keyword;
import io.github.aleknik.scientificcenterservice.model.domain.Paper;
import io.github.aleknik.scientificcenterservice.model.domain.UnregisteredAuthor;
import io.github.aleknik.scientificcenterservice.model.dto.CreatePaperRequestDto;
import io.github.aleknik.scientificcenterservice.service.PaperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/papers")
public class PaperController {

    private final PaperService paperService;

    public PaperController(PaperService paperService) {
        this.paperService = paperService;
    }

    @PostMapping
    ResponseEntity createPaper(@RequestPart("data") @Valid CreatePaperRequestDto createPaperRequestDto,
                               @RequestPart("file") @Valid @NotNull @NotBlank MultipartFile file) {

        final Paper paper = paperService.createPaper(convertToPaper(createPaperRequestDto), file);

        return ResponseEntity.ok(paper);
    }

    private Paper convertToPaper(CreatePaperRequestDto createPaperRequestDto) {

        final Paper paper = new Paper();

        paper.setTitle(createPaperRequestDto.getTitle());
        paper.setKeywords(createPaperRequestDto.getKeywords().stream()
                .map(Keyword::new).collect(Collectors.toSet()));
        paper.setCoauthors(createPaperRequestDto.getCoauthors().stream()
        .map(c -> new UnregisteredAuthor(c.getFirstName(),
                c.getLastName(),
                new Address(c.getCity(), c.getCountry()),
                c.getEmail())).collect(Collectors.toSet()));


        return paper;
    }
}
