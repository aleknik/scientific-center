package io.github.aleknik.scientificcenterservice.repository;

import io.github.aleknik.scientificcenterservice.model.domain.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Month;
import java.util.Optional;

public interface IssueRepository extends JpaRepository<Issue, Long> {

    Optional<Issue> findByJournalIdAndYearAndMonth(long id, int year, Month month);
}
