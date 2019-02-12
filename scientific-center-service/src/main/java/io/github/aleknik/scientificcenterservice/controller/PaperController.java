package io.github.aleknik.scientificcenterservice.controller;

import io.github.aleknik.scientificcenterservice.controller.exception.BadRequestException;
import io.github.aleknik.scientificcenterservice.model.domain.*;
import io.github.aleknik.scientificcenterservice.model.dto.CreatePaperRequestDto;
import io.github.aleknik.scientificcenterservice.model.dto.JournalDto;
import io.github.aleknik.scientificcenterservice.model.dto.PaperDto;
import io.github.aleknik.scientificcenterservice.model.dto.elasticsearch.PaperSearchDto;
import io.github.aleknik.scientificcenterservice.model.dto.elasticsearch.PaperQueryDto;
import io.github.aleknik.scientificcenterservice.model.dto.payment.PaymentStatus;
import io.github.aleknik.scientificcenterservice.security.RoleConstants;
import io.github.aleknik.scientificcenterservice.service.PaperService;
import io.github.aleknik.scientificcenterservice.service.UserService;
import io.github.aleknik.scientificcenterservice.service.elasticsearch.PaperSearchService;
import io.github.aleknik.scientificcenterservice.service.payment.PaymentService;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/papers")
public class PaperController {

    private final PaperService paperService;
    private final UserService userService;
    private final PaperSearchService paperSearchService;
    private final PaymentService paymentService;

    public PaperController(PaperService paperService, UserService userService, PaperSearchService paperSearchService, PaymentService paymentService) {
        this.paperService = paperService;
        this.userService = userService;
        this.paperSearchService = paperSearchService;
        this.paymentService = paymentService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + RoleConstants.AUTHOR + "')")
    public ResponseEntity createPaper(@RequestPart("data") @Valid CreatePaperRequestDto createPaperRequestDto,
                                      @RequestPart("file") @Valid @NotNull @NotBlank MultipartFile file) {

        final Author author = (Author) userService.findCurrentUser();

        final Paper paper = paperService.createPaper(convertToPaper(createPaperRequestDto, author), file);
        paperService.publishPaper(paper.getId());

        return ResponseEntity.ok(paper.getId());
    }

    @PostMapping("/search")
    public ResponseEntity query(@RequestBody List<PaperQueryDto> query) {

        List<String> highlights = Arrays.asList("content", "paperAbstract");
        final List<PaperSearchDto> result = paperSearchService.search(query, highlights);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable long id) {
        final Paper paper = paperService.findById(id);

        final PaperDto paperDto = new PaperDto();
        paperDto.setTitle(paper.getTitle());
        paperDto.setPaperAbstract(paper.getPaperAbstract());
        paperDto.setPrice(paper.getJournal().getPaperPrice());

        final JournalDto journalDto = new JournalDto();
        journalDto.setOpenAccess(paper.getJournal().isOpenAccess());
        journalDto.setName(paper.getJournal().getName());
        paperDto.setJournalDto(journalDto);

        return ResponseEntity.ok(paperDto);

    }

    @GetMapping("/download/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity download(@PathVariable long id) {
        final User user = userService.findCurrentUser();
        final Paper paper = paperService.findById(id);

        if (!paper.getJournal().isOpenAccess()) {
            PaymentStatus status = PaymentStatus.CANCELED;
            try {
                status = paymentService.paperStatus(id, user);
            } catch (Exception ignored) {
            }
            if (status != PaymentStatus.SUCCESS) {
                throw new BadRequestException("Paper is not bought");
            }
        }

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
        paper.setScienceField(createPaperRequestDto.getScienceField());
        paper.setKeywords(createPaperRequestDto.getKeywords().stream()
                .map(Keyword::new).collect(Collectors.toSet()));
        paper.setCoauthors(createPaperRequestDto.getCoauthors().stream()
        .map(c -> new UnregisteredAuthor(c.getFirstName(),
                c.getLastName(),
                new Address(c.getCity(), c.getCountry()),
                c.getEmail())).collect(Collectors.toSet()));
        paper.setReviewers(new HashSet<>(createPaperRequestDto.getReviewers()));

        return paper;
    }
}
