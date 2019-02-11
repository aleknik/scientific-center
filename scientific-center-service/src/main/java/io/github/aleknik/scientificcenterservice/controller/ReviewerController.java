package io.github.aleknik.scientificcenterservice.controller;

import io.github.aleknik.scientificcenterservice.model.domain.Paper;
import io.github.aleknik.scientificcenterservice.model.dto.elasticsearch.ReviewerQueryDto;
import io.github.aleknik.scientificcenterservice.model.dto.elasticsearch.ReviewerSearchDto;
import io.github.aleknik.scientificcenterservice.service.PaperService;
import io.github.aleknik.scientificcenterservice.service.elasticsearch.PaperSearchService;
import io.github.aleknik.scientificcenterservice.service.elasticsearch.ReviewerSearchService;
import io.github.aleknik.scientificcenterservice.service.util.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/reviewers")
public class ReviewerController {

    private final ReviewerSearchService reviewerSearchService;
    private final PaperService paperService;
    private final PaperSearchService paperSearchService;
    private final StorageService storageService;

    public ReviewerController(ReviewerSearchService reviewerSearchService, PaperService paperService, PaperSearchService paperSearchService, StorageService storageService) {
        this.reviewerSearchService = reviewerSearchService;
        this.paperService = paperService;
        this.paperSearchService = paperSearchService;
        this.storageService = storageService;
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
                results.removeAll(moreLikeThisRes);
            } else {
                results = moreLikeThisRes;
            }
        }
        if (queryDto.isIncludeDistance()) {
            final HashSet<ReviewerSearchDto> distanceRes = new HashSet<>(reviewerSearchService.searchByJournalAndDistance(paper, 100));

            if (results != null) {
                results.removeAll(distanceRes);
            } else {
                results = distanceRes;
            }
        }

        return ResponseEntity.ok(results);
    }
}
