package io.github.aleknik.scientificcenterservice.repositroy;

import io.github.aleknik.scientificcenterservice.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
