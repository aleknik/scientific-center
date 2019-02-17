package io.github.aleknik.scientificcenterservice.controller;

import io.github.aleknik.scientificcenterservice.controller.exception.BadRequestException;
import io.github.aleknik.scientificcenterservice.model.domain.*;
import io.github.aleknik.scientificcenterservice.model.dto.*;
import io.github.aleknik.scientificcenterservice.model.dto.elasticsearch.PaperQueryDto;
import io.github.aleknik.scientificcenterservice.model.dto.elasticsearch.PaperSearchDto;
import io.github.aleknik.scientificcenterservice.model.dto.elasticsearch.ReviewerSearchDto;
import io.github.aleknik.scientificcenterservice.model.dto.payment.PaymentStatus;
import io.github.aleknik.scientificcenterservice.security.RoleConstants;
import io.github.aleknik.scientificcenterservice.service.JournalService;
import io.github.aleknik.scientificcenterservice.service.PaperService;
import io.github.aleknik.scientificcenterservice.service.UserService;
import io.github.aleknik.scientificcenterservice.service.elasticsearch.PaperSearchService;
import io.github.aleknik.scientificcenterservice.service.payment.PaymentService;
import io.github.aleknik.scientificcenterservice.service.process.ProcessService;
import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
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
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/papers")
public class PaperController {

    private final PaperService paperService;
    private final UserService userService;
    private final PaperSearchService paperSearchService;
    private final PaymentService paymentService;
    private final JournalService journalService;
    private final ProcessService processService;

    public PaperController(PaperService paperService,
                           UserService userService,
                           PaperSearchService paperSearchService,
                           PaymentService paymentService,
                           JournalService journalService, ProcessService processService) {
        this.paperService = paperService;
        this.userService = userService;
        this.paperSearchService = paperSearchService;
        this.paymentService = paymentService;
        this.journalService = journalService;
        this.processService = processService;
    }

    @PostMapping("{taskId}")
    @PreAuthorize("hasAuthority('" + RoleConstants.AUTHOR + "')")
    public ResponseEntity createPaper(@RequestPart("data") @Valid CreatePaperRequestDto createPaperRequestDto,
                                      @RequestPart("file") @Valid @NotNull @NotBlank MultipartFile file, @PathVariable String taskId) {

        final Author author = (Author) userService.findCurrentUser();

        final Paper paper = paperService.createPaper(convertToPaper(createPaperRequestDto, author), file);
//        paperService.publishPaper(paper.getId());

        final ArrayList<TaskFormFieldDto> taskFormFieldDtos = new ArrayList<>();
        taskFormFieldDtos.add(new TaskFormFieldDto("paperId", String.valueOf(paper.getId())));
        processService.submitTaskForm(taskId, taskFormFieldDtos);

        return ResponseEntity.ok(paper.getId());
    }

    @PostMapping("/start-paper-process")
    public ResponseEntity startPaperSubmissionProcess() {
        final User currentUser = userService.findCurrentUser();
        Map<String, VariableValueDto> variables = new HashMap<>();
        final VariableValueDto variableValueDto = new VariableValueDto();
        variableValueDto.setValue(currentUser.getUsername());
        variables.put("authorId", variableValueDto);
        final String processId = processService.startProcess("PaperSubmission", variables);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/resubmit/{taskId}")
    @PreAuthorize("hasAuthority('" + RoleConstants.AUTHOR + "')")
    public ResponseEntity revisePaper(@RequestPart("data") @Valid List<ReviewDto> reviewDtos,
                                      @RequestPart("file") @Valid @NotNull @NotBlank MultipartFile file, @PathVariable String taskId) {

        final TaskDto task = processService.getTask(taskId);
        final String paperId = (String) processService.getVariable(task.getProcessInstanceId(), "paperId");

        paperService.setReviewMessages(reviewDtos);
        paperService.submitRevision(Long.parseLong(paperId), file);

        final ArrayList<TaskFormFieldDto> taskFormFieldDtos = new ArrayList<>();
        processService.submitTaskForm(taskId, taskFormFieldDtos);

        return ResponseEntity.noContent().build();
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
        paperDto.setDOI(paper.getDOI());
        paperDto.setPaperAbstract(paper.getPaperAbstract());
        paperDto.setPrice(paper.getJournal().getPaperPrice());
        paperDto.setKeywords(paper.getKeywords().stream().map(Keyword::getName).collect(Collectors.toList()));

        final JournalDto journalDto = new JournalDto();
        journalDto.setOpenAccess(paper.getJournal().isOpenAccess());
        journalDto.setName(paper.getJournal().getName());
        paperDto.setJournal(journalDto);

        return ResponseEntity.ok(paperDto);

    }

    @GetMapping("/download/task/{taskId}")
    public ResponseEntity downloadByTask(@PathVariable String taskId) {

        final TaskDto task = processService.getTask(taskId);
        final String paperId = (String) processService.getVariable(task.getProcessInstanceId(), "paperId");

        return downloadPdf(Long.parseLong(paperId));

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

        return downloadPdf(id);

    }

    private ResponseEntity downloadPdf(long id) {
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

    @GetMapping("/task/{taskId}")
    public ResponseEntity getPaperByTaskId(@PathVariable String taskId) {
        final TaskDto task = processService.getTask(taskId);
        final String paperId = (String) processService.getVariable(task.getProcessInstanceId(), "paperId");

        return ResponseEntity.ok(new PaperDto(paperService.findById(Long.parseLong(paperId))));
    }

    @PostMapping("/relevant/{taskId}")
    public ResponseEntity paperRelevant(@PathVariable String taskId, @RequestBody List<TaskFormFieldDto> formFieldDtos) {
        processService.submitTaskForm(taskId, formFieldDtos);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reviewers/{taskId}")
    public ResponseEntity chooseReviewers(@PathVariable String taskId, @RequestBody List<ReviewerSearchDto> reviewerDtos, @RequestParam String date) {
        final TaskDto task = processService.getTask(taskId);
        final String paperId = (String) processService.getVariable(task.getProcessInstanceId(), "paperId");

        final Paper paperEntity = paperService.findById(Long.parseLong(paperId));

        if (!paperEntity.getReviewers().isEmpty()) {
            if (reviewerDtos.size() != 1) {
                throw new BadRequestException("You can choose only one reviewer for extra review");
            }
            final ReviewerSearchDto reviewerSearchDto = reviewerDtos.get(0);
            if (paperEntity.getReviewers().stream().anyMatch(r -> r.getId() == reviewerSearchDto.getId())) {
                throw new BadRequestException("This reviewer already reviewed this paper");
            }
        } else if (reviewerDtos.size() < 2) {
            throw new BadRequestException("You must chose two reviewers at minimum");
        }

        paperService.setReviewers(Long.parseLong(paperId), reviewerDtos.stream().map(dto -> {
            final Reviewer reviewer = new Reviewer();
            reviewer.setId(dto.getId());
            return reviewer;
        }).collect(Collectors.toList()));


        final ArrayList<TaskFormFieldDto> taskFormFieldDtos = new ArrayList<>();
        taskFormFieldDtos.add(new TaskFormFieldDto("reviewers", new ArrayList<>(reviewerDtos.stream().map(r -> userService.findById(r.getId()))
                .map(User::getUsername).collect(Collectors.toList()))));
        taskFormFieldDtos.add(new TaskFormFieldDto("reviewDate", date));
        processService.submitTaskForm(taskId, taskFormFieldDtos);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/new-reviewer/{taskId}")
    public ResponseEntity chooseNewReviewers(@PathVariable String taskId, @RequestBody ReviewerSearchDto reviewerDto, @RequestParam String date) {
        final TaskDto task = processService.getTask(taskId);
        final String paperId = (String) processService.getVariable(task.getProcessInstanceId(), "paperId");

        final Paper paperEntity = paperService.findById(Long.parseLong(paperId));

        if (paperEntity.getReviewers().stream().anyMatch(r -> r.getId() == reviewerDto.getId())) {
            throw new BadRequestException("This reviewer already reviewed this paper");
        }

        final Reviewer reviewer = new Reviewer();
        reviewer.setId(reviewerDto.getId());
        paperService.setReviewer(Long.parseLong(paperId), reviewer);

        final User user = userService.findById(reviewerDto.getId());
        final ArrayList<TaskFormFieldDto> taskFormFieldDtos = new ArrayList<>();
        taskFormFieldDtos.add(new TaskFormFieldDto("reviewer", user.getUsername()));
        taskFormFieldDtos.add(new TaskFormFieldDto("reviewDate", date));
        processService.submitTaskForm(taskId, taskFormFieldDtos);

        return ResponseEntity.noContent().build();
    }

    private Paper convertToPaper(CreatePaperRequestDto createPaperRequestDto, Author author) {

        Random rand = new Random();
        final List<Journal> journals = journalService.findAll();
        final Journal journal = journals.get(rand.nextInt(journals.size()));

        final Paper paper = new Paper();
        paper.setJournal(journal);

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
        return paper;
    }

    @GetMapping("/format-data/{taskId}")
    public ResponseEntity getPaperFormatData(@PathVariable String taskId) {
        final TaskDto task = processService.getTask(taskId);
        final String paperId = (String) processService.getVariable(task.getProcessInstanceId(), "paperId");
        final String message = (String) processService.getVariable(task.getProcessInstanceId(), "message");


        final Paper paper = paperService.findById(Long.parseLong(paperId));

        return ResponseEntity.ok(new FormatPaperDto(new PaperDto(paper), message));
    }


    @GetMapping("/reviews/{taskId}")
    public ResponseEntity getReviews(@PathVariable String taskId) {
        final TaskDto task = processService.getTask(taskId);
        final String paperId = (String) processService.getVariable(task.getProcessInstanceId(), "paperId");

        final Paper paper = paperService.findById(Long.parseLong(paperId));

        return ResponseEntity.ok(paper.getReviews().stream().map(pr -> new ReviewDto(pr, true)).collect(Collectors.toList()));
    }

}
