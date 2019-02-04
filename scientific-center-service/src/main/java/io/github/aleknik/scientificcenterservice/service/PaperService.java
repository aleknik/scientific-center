package io.github.aleknik.scientificcenterservice.service;

import io.github.aleknik.scientificcenterservice.model.domain.Address;
import io.github.aleknik.scientificcenterservice.model.domain.Paper;
import io.github.aleknik.scientificcenterservice.model.domain.UnregisteredAuthor;
import io.github.aleknik.scientificcenterservice.repository.PaperRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PaperService {

    private final PaperRepository paperRepository;
    private final StorageService storageService;
    private final GeocodingService geocodingService;

    public PaperService(PaperRepository paperRepository, StorageService storageService, GeocodingService geocodingService) {
        this.paperRepository = paperRepository;
        this.storageService = storageService;
        this.geocodingService = geocodingService;
    }

    public Paper createPaper(Paper paper, MultipartFile file) {
        for (UnregisteredAuthor coauthor : paper.getCoauthors()) {
            final Address adress = geocodingService.getAddress(coauthor.getAddress().getCity(), coauthor.getAddress().getCountry());
            coauthor.setAddress(adress);
        }
        final Paper savedPaper = paperRepository.save(paper);
        storageService.store(file, String.valueOf(savedPaper.getId()));
        return paper;
    }

}
