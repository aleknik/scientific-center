package io.github.aleknik.scientificcenterservice.controller;

import io.github.aleknik.scientificcenterservice.model.dto.elasticsearch.ReviewerSearchDto;
import io.github.aleknik.scientificcenterservice.service.elasticsearch.ReviewerSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reviewers")
public class ReviewerController {

    private final ReviewerSearchService reviewerSearchService;

    public ReviewerController(ReviewerSearchService reviewerSearchService) {
        this.reviewerSearchService = reviewerSearchService;
    }

    @PostMapping("/search/fields")
    public ResponseEntity query(@RequestParam long journalId, @RequestParam long fieldId) {
        final List<ReviewerSearchDto> reviewerSearchDtos = reviewerSearchService.searchByJournalAndField(journalId, fieldId);

        return ResponseEntity.ok(reviewerSearchDtos);
    }

    @PostMapping("/search/like")
    public ResponseEntity query(@RequestParam long journalId, @RequestParam String text) {
        final List<ReviewerSearchDto> reviewerSearchDtos = reviewerSearchService.searchByJournalAndLikePaper(journalId, text);

        return ResponseEntity.ok(reviewerSearchDtos);
    }
}
