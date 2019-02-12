package io.github.aleknik.scientificcenterservice.service;

import io.github.aleknik.scientificcenterservice.model.domain.Reviewer;
import io.github.aleknik.scientificcenterservice.repository.ReviewerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewerService {

    private final ReviewerRepository reviewerRepository;

    public ReviewerService(ReviewerRepository reviewerRepository) {
        this.reviewerRepository = reviewerRepository;
    }

    public List<Reviewer> findAll() {
        return reviewerRepository.findAll();
    }
}
