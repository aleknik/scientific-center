package io.github.aleknik.scientificcenterservice.service;

import io.github.aleknik.scientificcenterservice.controller.exception.BadRequestException;
import io.github.aleknik.scientificcenterservice.controller.exception.NotFoundException;
import io.github.aleknik.scientificcenterservice.model.domain.*;
import io.github.aleknik.scientificcenterservice.repository.*;
import io.github.aleknik.scientificcenterservice.service.elasticsearch.PaperSearchService;
import io.github.aleknik.scientificcenterservice.service.util.GeocodingService;
import io.github.aleknik.scientificcenterservice.service.util.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PaperService {

    private final PaperRepository paperRepository;
    private final JournalRepository journalRepository;
    private final PaperSearchService paperSearchService;
    private final IssueRepository issueRepository;
    private final ScienceFieldRepository scienceFieldRepository;
    private final ReviewerRepository reviewerRepository;

    private final StorageService storageService;
    private final GeocodingService geocodingService;


    public PaperService(PaperRepository paperRepository,
                        JournalRepository journalRepository,
                        PaperSearchService paperSearchService,
                        IssueRepository issueRepository, ScienceFieldRepository scienceFieldRepository, ReviewerRepository reviewerRepository, StorageService storageService,
                        GeocodingService geocodingService) {
        this.paperRepository = paperRepository;
        this.journalRepository = journalRepository;
        this.paperSearchService = paperSearchService;
        this.issueRepository = issueRepository;
        this.scienceFieldRepository = scienceFieldRepository;
        this.reviewerRepository = reviewerRepository;
        this.storageService = storageService;
        this.geocodingService = geocodingService;
    }

    public Paper createPaper(Paper paper, MultipartFile file) {
        final ScienceField field = scienceFieldRepository.findById(paper.getScienceField().getId()).orElseThrow(() -> new NotFoundException("Field not found"));
        paper.setScienceField(field);
        for (UnregisteredAuthor coauthor : paper.getCoauthors()) {
            final Address address = geocodingService.getAddress(coauthor.getAddress().getCity(), coauthor.getAddress().getCountry());
            coauthor.setAddress(address);
        }

        paper.setReviewers(paper.getReviewers().stream().map(r ->
                reviewerRepository.findById(r.getId()).orElseThrow(() -> new NotFoundException("Reviewer not found")))
                .collect(Collectors.toSet()));

        final Journal journal = journalRepository.findById(paper.getJournal().getId()).orElseThrow(BadRequestException::new);

        paper.setJournal(journal);
        final Paper savedPaper = paperRepository.save(paper);
        storageService.store(file, String.valueOf(savedPaper.getId()));
        return paper;
    }

    public byte[] getPaperPdf(long id) {
        return storageService.load(String.valueOf(id));
    }

    public Paper findById(long id) {
        return paperRepository.findById(id).orElseThrow(() -> new NotFoundException("Paper not found"));
    }

    public void publishPaper(long id) {
        final Paper paper = findById(id);
        final LocalDateTime now = LocalDateTime.now();
        paper.setPublishDate(now);

        final Month month = now.getMonth();
        final int year = now.getYear();

        final Issue issue = issueRepository.findByJournalIdAndYearAndMonth(paper.getJournal().getId(), year, month)
                .orElse(new Issue(paper.getJournal(), year, month));

        paper.setIssue(issue);
        issueRepository.save(issue);
        paperRepository.save(paper);

        paperSearchService.indexPaper(id);
    }

    public Paper setReviewers(long paperId, List<Reviewer> reviewers) {
        final Paper paper = findById(paperId);
        final Set<Reviewer> foundReviewers = reviewers.stream()
                .map(r -> reviewerRepository.findById(r.getId()).orElseThrow(() -> new BadRequestException("Reviewer not found")))
                .collect(Collectors.toSet());

        paper.setReviewers(foundReviewers);

        return paperRepository.save(paper);
    }
}
