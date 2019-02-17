package io.github.aleknik.scientificcenterservice.repository;

import io.github.aleknik.scientificcenterservice.model.domain.PaperReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperReviewRepository extends JpaRepository<PaperReview, Long> {
}
