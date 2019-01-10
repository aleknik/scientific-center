package io.github.aleknik.scientificcenterservice.repositroy;

import io.github.aleknik.scientificcenterservice.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
