package io.github.aleknik.scientificcenterservice.repository;

import io.github.aleknik.scientificcenterservice.model.domain.Reviewer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewerRepository  extends JpaRepository<Reviewer, Long> {
}
