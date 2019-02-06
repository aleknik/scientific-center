package io.github.aleknik.scientificcenterservice.service;

import io.github.aleknik.scientificcenterservice.controller.exception.NotFoundException;
import io.github.aleknik.scientificcenterservice.model.domain.Issue;
import io.github.aleknik.scientificcenterservice.repository.IssueRepository;
import org.springframework.stereotype.Service;

@Service
public class IssueService {

    private final IssueRepository issueRepository;

    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public Issue findById(long id) {
        return issueRepository.findById(id).orElseThrow(() -> new NotFoundException("Issue not found"));
    }
}
