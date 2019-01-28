package io.github.aleknik.scientificcenterservice.controller;

import io.github.aleknik.scientificcenterservice.model.elasticsearch.PaperIndexUnit;
import io.github.aleknik.scientificcenterservice.model.elasticsearch.Reviewer;
import io.github.aleknik.scientificcenterservice.repository.elasticsearch.PaperRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("api/test")
public class TestController {

    private final PaperRepository paperRepository;

    public TestController(PaperRepository paperRepository) {
        this.paperRepository = paperRepository;
    }

    @GetMapping
    public ResponseEntity test() {
        final PaperIndexUnit paperIndexUnit = new PaperIndexUnit();
        paperIndexUnit.setTitle("test");
        paperIndexUnit.setContent("sadasas");
        paperIndexUnit.setExternalId("1");
        paperIndexUnit.setJournal("dsfsd");
        paperIndexUnit.setKeywords(Arrays.asList("Earth", "Mars", "Venus"));
        paperIndexUnit.setOpenAccess(true);
        paperIndexUnit.setReviewers(Arrays.asList(new Reviewer("2", "sada", "sdad")));

        final PaperIndexUnit index = paperRepository.index(paperIndexUnit);
        return ResponseEntity.ok(index);
    }
}
