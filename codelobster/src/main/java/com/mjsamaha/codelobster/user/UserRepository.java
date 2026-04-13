package com.mjsamaha.codelobster.user;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameIgnoreCase(String username);

    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findByVerificationToken(String verificationToken);

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByEmailIgnoreCase(String email);

    @Query("""
        SELECT u
        FROM User u
        WHERE u.enabled = true
        ORDER BY u.rating DESC, u.problemSolved DESC, u.createdAt ASC
        """)
    Page<User> findLeaderboard(Pageable pageable);
}
