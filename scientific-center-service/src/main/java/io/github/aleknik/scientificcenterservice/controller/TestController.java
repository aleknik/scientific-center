package io.github.aleknik.scientificcenterservice.controller;

import io.github.aleknik.scientificcenterservice.model.elasticsearch.PaperIndexUnit;
import io.github.aleknik.scientificcenterservice.repository.elasticsearch.ESPaperRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("api/test")
public class TestController {

    private final ESPaperRepository ESPaperRepository;

    public TestController(ESPaperRepository ESPaperRepository) {
        this.ESPaperRepository = ESPaperRepository;
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
//        paperIndexUnit.setReviewers(Arrays.asList(new Reviewer("2", "sada", "sdad")));

        final PaperIndexUnit index = ESPaperRepository.index(paperIndexUnit);
        return ResponseEntity.ok(index);
    }
}
