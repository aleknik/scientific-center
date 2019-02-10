package io.github.aleknik.scientificcenterservice.repository;

import io.github.aleknik.scientificcenterservice.model.domain.ScienceField;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScienceFieldRepository extends JpaRepository<ScienceField, Long> {
}
