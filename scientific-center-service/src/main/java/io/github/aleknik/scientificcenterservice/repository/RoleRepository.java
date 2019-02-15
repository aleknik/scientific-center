package io.github.aleknik.scientificcenterservice.repository;

import io.github.aleknik.scientificcenterservice.model.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
