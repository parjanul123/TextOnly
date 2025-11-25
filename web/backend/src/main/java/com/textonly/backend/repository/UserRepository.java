package com.textonly.backend.repository;

import com.textonly.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // ✅ Caută un utilizator după email
    Optional<User> findByEmail(String email);

    // ✅ Caută un utilizator după nume de utilizator
    Optional<User> findByUsername(String username);

    // ✅ Verifică dacă un email este deja folosit
    boolean existsByEmail(String email);

    // ✅ Verifică dacă un username este deja folosit
    boolean existsByUsername(String username);
}
