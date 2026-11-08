package com.mat.Backend.application.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("""
        SELECT u
        FROM User u
        WHERE NOT EXISTS (
            SELECT p
            FROM Participant p
            WHERE p.user = u
              AND p.event.id = :eventId
        )
        AND NOT EXISTS (
            SELECT o
            FROM Organizer o
            WHERE o.user = u
              AND o.event.id = :eventId
        )
    """)
    List<User> findUsersNotInEvent(@Param("eventId") Long eventId);
}
