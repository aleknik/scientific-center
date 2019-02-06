package io.github.aleknik.scientificcenterservice.service;

import io.github.aleknik.scientificcenterservice.controller.exception.BadRequestException;
import io.github.aleknik.scientificcenterservice.controller.exception.NotFoundException;
import io.github.aleknik.scientificcenterservice.model.domain.*;
import io.github.aleknik.scientificcenterservice.repository.IssueRepository;
import io.github.aleknik.scientificcenterservice.repository.JournalRepository;
import io.github.aleknik.scientificcenterservice.repository.PaperRepository;
import io.github.aleknik.scientificcenterservice.service.elasticsearch.PaperSearchService;
import io.github.aleknik.scientificcenterservice.service.util.GeocodingService;
import io.github.aleknik.scientificcenterservice.service.util.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.Month;

@Service
public class PaperService {

    private final PaperRepository paperRepository;
    private final JournalRepository journalRepository;
    private final PaperSearchService paperSearchService;
    private final IssueRepository issueRepository;

    private final StorageService storageService;
    private final GeocodingService geocodingService;


    public PaperService(PaperRepository paperRepository,
                        JournalRepository journalRepository,
                        PaperSearchService paperSearchService,
                        IssueRepository issueRepository, StorageService storageService,
                        GeocodingService geocodingService) {
        this.paperRepository = paperRepository;
        this.journalRepository = journalRepository;
        this.paperSearchService = paperSearchService;
        this.issueRepository = issueRepository;
        this.storageService = storageService;
        this.geocodingService = geocodingService;
    }

    public Paper createPaper(Paper paper, MultipartFile file) {
        for (UnregisteredAuthor coauthor : paper.getCoauthors()) {
            final Address address = geocodingService.getAddress(coauthor.getAddress().getCity(), coauthor.getAddress().getCountry());
            coauthor.setAddress(address);
        }

        final Journal journal = journalRepository.findAll().stream().findFirst().orElseThrow(BadRequestException::new);

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
}
