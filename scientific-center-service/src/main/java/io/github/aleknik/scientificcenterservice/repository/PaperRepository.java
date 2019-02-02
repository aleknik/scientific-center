package io.github.aleknik.scientificcenterservice.repository;

import io.github.aleknik.scientificcenterservice.model.domain.Paper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperRepository extends JpaRepository<Paper, Long> {

}
