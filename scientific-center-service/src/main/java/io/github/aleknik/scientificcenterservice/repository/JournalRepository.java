package io.github.aleknik.scientificcenterservice.repository;

import io.github.aleknik.scientificcenterservice.model.domain.Journal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalRepository extends JpaRepository<Journal, Long> {
}
