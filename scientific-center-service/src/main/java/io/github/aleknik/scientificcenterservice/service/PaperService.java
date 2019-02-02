package io.github.aleknik.scientificcenterservice.service;

import io.github.aleknik.scientificcenterservice.model.domain.Paper;
import io.github.aleknik.scientificcenterservice.repository.PaperRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PaperService {

    private final PaperRepository paperRepository;
    private final StorageService storageService;

    public PaperService(PaperRepository paperRepository, StorageService storageService) {
        this.paperRepository = paperRepository;
        this.storageService = storageService;
    }

    public Paper createPaper(Paper paper, MultipartFile file) {
        final Paper savedPaper = paperRepository.save(paper);
        storageService.store(file, String.valueOf(savedPaper.getId()));
        return paper;
    }
}
