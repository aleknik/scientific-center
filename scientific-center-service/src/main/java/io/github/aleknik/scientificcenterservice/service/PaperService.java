package io.github.aleknik.scientificcenterservice.service;

import io.github.aleknik.scientificcenterservice.controller.exception.BadRequestException;
import io.github.aleknik.scientificcenterservice.controller.exception.NotFoundException;
import io.github.aleknik.scientificcenterservice.model.domain.Address;
import io.github.aleknik.scientificcenterservice.model.domain.Journal;
import io.github.aleknik.scientificcenterservice.model.domain.Paper;
import io.github.aleknik.scientificcenterservice.model.domain.UnregisteredAuthor;
import io.github.aleknik.scientificcenterservice.repository.JournalRepository;
import io.github.aleknik.scientificcenterservice.repository.PaperRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PaperService {

    private final PaperRepository paperRepository;
    private final JournalRepository journalRepository;

    private final StorageService storageService;
    private final GeocodingService geocodingService;


    public PaperService(PaperRepository paperRepository, JournalRepository journalRepository, StorageService storageService, GeocodingService geocodingService) {
        this.paperRepository = paperRepository;
        this.journalRepository = journalRepository;
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
}
