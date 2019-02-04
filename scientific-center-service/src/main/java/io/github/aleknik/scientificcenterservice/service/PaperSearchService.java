package io.github.aleknik.scientificcenterservice.service;

import io.github.aleknik.scientificcenterservice.controller.exception.BadRequestException;
import io.github.aleknik.scientificcenterservice.handler.PDFHandler;
import io.github.aleknik.scientificcenterservice.model.domain.Keyword;
import io.github.aleknik.scientificcenterservice.model.domain.Paper;
import io.github.aleknik.scientificcenterservice.model.dto.PaperSearchDto;
import io.github.aleknik.scientificcenterservice.model.elasticsearch.Author;
import io.github.aleknik.scientificcenterservice.model.elasticsearch.PaperIndexUnit;
import io.github.aleknik.scientificcenterservice.model.elasticsearch.Reviewer;
import io.github.aleknik.scientificcenterservice.repository.PaperRepository;
import io.github.aleknik.scientificcenterservice.repository.elasticsearch.ESPaperRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaperSearchService {

    private final PDFHandler pdfHandler;
    private final ESPaperRepository esPaperRepository;
    private final StorageService storageService;
    private final PaperRepository paperRepository;

    public PaperSearchService(PDFHandler pdfHandler, ESPaperRepository esPaperRepository, StorageService storageService, PaperRepository paperRepository) {
        this.pdfHandler = pdfHandler;
        this.esPaperRepository = esPaperRepository;
        this.storageService = storageService;
        this.paperRepository = paperRepository;
    }

    public void indexPaper(long id) {
        final Paper paper = paperRepository.findById(id).orElseThrow(() -> new BadRequestException("Paper not found"));
        final byte[] data = storageService.load(String.valueOf(id));
        final String text = pdfHandler.getText(data);


        esPaperRepository.index(convertPaperToIndexUnit(paper, text));
    }

    private PaperIndexUnit convertPaperToIndexUnit(Paper paper, String text) {

        final PaperIndexUnit paperIndexUnit = new PaperIndexUnit();

        paperIndexUnit.setOpenAccess(paper.getJournal().isOpenAccess());
        paperIndexUnit.setKeywords(paper.getKeywords().stream().map(Keyword::getName).collect(Collectors.toList()));
        paperIndexUnit.setExternalId(String.valueOf(paper.getId()));
        paperIndexUnit.setTitle(paper.getTitle());
        paperIndexUnit.setJournal(paper.getJournal().getName());

        final Author author = new Author(String.valueOf(paper.getAuthor().getId()),
                paper.getAuthor().getFirstName(),
                paper.getAuthor().getLastName(),
                paper.getAuthor().getAddress().getLatitude(),
                paper.getAuthor().getAddress().getLongitude());

        paperIndexUnit.setAuthor(author);

        paperIndexUnit.setCoauthors(paper.getCoauthors().stream()
                .map(ca -> new Author(null, ca.getFirstName(),
                        ca.getLastName(),
                        ca.getAddress().getLatitude(),
                        ca.getAddress().getLongitude()))
                .collect(Collectors.toList()));
        paperIndexUnit.setContent(text);
        paperIndexUnit.setReviewers(paper.getReviewers().stream()
                .map(ca -> new Reviewer(String.valueOf(ca.getId()), ca.getFirstName(),
                        ca.getLastName(),
                        ca.getAddress().getLatitude(),
                        ca.getAddress().getLongitude()))
                .collect(Collectors.toList()));

        return paperIndexUnit;
    }

    public List<PaperSearchDto> search() {
        final ArrayList<PaperSearchDto> searchDtos = new ArrayList<>();
        for (PaperIndexUnit unit : esPaperRepository.findAll()) {
            final PaperSearchDto paperSearchDto = new PaperSearchDto();
            paperSearchDto.setId(Long.parseLong(unit.getExternalId()));
            paperSearchDto.setOpenAccess(unit.isOpenAccess());
            paperSearchDto.setTitle(unit.getTitle());

            searchDtos.add(paperSearchDto);
        }

        return searchDtos;
    }

}