package io.github.aleknik.scientificcenterservice.controller;

import io.github.aleknik.scientificcenterservice.model.domain.Paper;
import io.github.aleknik.scientificcenterservice.model.dto.elasticsearch.ReviewerQueryDto;
import io.github.aleknik.scientificcenterservice.model.dto.elasticsearch.ReviewerSearchDto;
import io.github.aleknik.scientificcenterservice.service.PaperService;
import io.github.aleknik.scientificcenterservice.service.ReviewerService;
import io.github.aleknik.scientificcenterservice.service.elasticsearch.PaperSearchService;
import io.github.aleknik.scientificcenterservice.service.elasticsearch.ReviewerSearchService;
import io.github.aleknik.scientificcenterservice.service.util.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviewers")
public class ReviewerController {

    private final ReviewerSearchService reviewerSearchService;
    private final PaperService paperService;
    private final PaperSearchService paperSearchService;
    private final StorageService storageService;

    private final ReviewerService reviewerService;

    public ReviewerController(ReviewerSearchService reviewerSearchService, PaperService paperService, PaperSearchService paperSearchService, StorageService storageService, ReviewerService reviewerService) {
        this.reviewerSearchService = reviewerSearchService;
        this.paperService = paperService;
        this.paperSearchService = paperSearchService;
        this.storageService = storageService;
        this.reviewerService = reviewerService;
    }

    @GetMapping
    public ResponseEntity findAll() {
        final List<ReviewerSearchDto> result = reviewerService.findAll().stream().map(r -> new ReviewerSearchDto(r.getId(), r.getFirstName(), r.getLastName())).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @PostMapping("/search")
    public ResponseEntity query(@RequestBody ReviewerQueryDto queryDto) {
        final Paper paper = paperService.findById(queryDto.getPaperId());

        Set<ReviewerSearchDto> results = null;

        if (queryDto.isIncludeScienceField()) {
            results = new HashSet<>(reviewerSearchService.searchByJournalAndField(paper.getJournal().getId(), paper.getScienceField().getId()));
        }
        if (queryDto.isIncludeMoreLikeThis()) {
            final String content = paperSearchService.getContent(paper.getId());
            final HashSet<ReviewerSearchDto> moreLikeThisRes = new HashSet<>(reviewerSearchService.searchByJournalAndLikePaper(paper.getJournal().getId(), content));

            if (results != null) {
                results.retainAll(moreLikeThisRes);
            } else {
                results = moreLikeThisRes;
            }
        }
        if (queryDto.isIncludeDistance()) {
            final HashSet<ReviewerSearchDto> distanceRes = new HashSet<>(reviewerSearchService.searchByJournalAndDistance(paper, 100));

            if (results != null) {
                results.retainAll(distanceRes);
            } else {
                results = distanceRes;
            }
        }

        return ResponseEntity.ok(results);
    }
}
