package io.github.aleknik.scientificcenterservice.controller;

import io.github.aleknik.scientificcenterservice.model.domain.*;
import io.github.aleknik.scientificcenterservice.model.dto.CreatePaperRequestDto;
import io.github.aleknik.scientificcenterservice.model.dto.PaperResponse;
import io.github.aleknik.scientificcenterservice.model.dto.PaperSearchDto;
import io.github.aleknik.scientificcenterservice.security.RoleConstants;
import io.github.aleknik.scientificcenterservice.service.PaperSearchService;
import io.github.aleknik.scientificcenterservice.service.PaperService;
import io.github.aleknik.scientificcenterservice.service.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/papers")
public class PaperController {

    private final PaperService paperService;
    private final UserService userService;
    private final PaperSearchService paperSearchService;

    public PaperController(PaperService paperService, UserService userService, PaperSearchService paperSearchService) {
        this.paperService = paperService;
        this.userService = userService;
        this.paperSearchService = paperSearchService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + RoleConstants.AUTHOR + "')")
    public ResponseEntity createPaper(@RequestPart("data") @Valid CreatePaperRequestDto createPaperRequestDto,
                                      @RequestPart("file") @Valid @NotNull @NotBlank MultipartFile file) {

        final Author author = (Author) userService.findCurrentUser();

        final Paper paper = paperService.createPaper(convertToPaper(createPaperRequestDto, author), file);
        paperSearchService.indexPaper(paper.getId());

        return ResponseEntity.ok(paper.getId());
    }

    @PostMapping("/search")
    public ResponseEntity query() {

        final List<PaperSearchDto> result = paperSearchService.search();

        return ResponseEntity.ok(result);

    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable long id) {
        final Paper paper = paperService.findById(id);

        final PaperResponse paperResponse = new PaperResponse();
        paperResponse.setTitle(paper.getTitle());
        paperResponse.setPaperAbstract(paper.getPaperAbstract());
        paperResponse.setPrice(paper.getJournal().getPaperPrice());

        return ResponseEntity.ok(paperResponse);

    }

    @GetMapping("/download/{id}")
    public ResponseEntity download(@PathVariable long id) {
        final byte[] data = paperService.getPaperPdf(id);

        ByteArrayResource resource = new ByteArrayResource(data);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + UUID.randomUUID().toString() + ".pdf");


        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(data.length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);

    }

    private Paper convertToPaper(CreatePaperRequestDto createPaperRequestDto, Author author) {

        final Paper paper = new Paper();

        paper.setAuthor(author);
        paper.setPaperAbstract(createPaperRequestDto.getPaperAbstract());
        paper.setTitle(createPaperRequestDto.getTitle());
        paper.setKeywords(createPaperRequestDto.getKeywords().stream()
                .map(Keyword::new).collect(Collectors.toSet()));
        paper.setCoauthors(createPaperRequestDto.getCoauthors().stream()
        .map(c -> new UnregisteredAuthor(c.getFirstName(),
                c.getLastName(),
                new Address(c.getCity(), c.getCountry()),
                c.getEmail())).collect(Collectors.toSet()));


        return paper;
    }
}
