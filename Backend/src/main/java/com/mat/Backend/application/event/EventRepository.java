package com.mat.Backend.application.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT p.event FROM Participant p WHERE p.user.id = :userId")
    List<Event> findEventsWithUserParticipating(@Param("userId") Long userId);

    @Query("SELECT o.event FROM Organizer o WHERE o.user.id = :userId")
    List<Event> findEventsWithUserOrganizing(@Param("userId") Long userId);


    @Query("""
        SELECT COUNT(e) > 0
        FROM Event e
        WHERE LOWER(e.location) = LOWER(:location)
        AND e.fromTime < :toTime
        AND e.toTime > :fromTime
    """)
    boolean existsEventConflict(
            @Param("location") String location,
            @Param("fromTime") LocalDateTime fromTime,
            @Param("toTime") LocalDateTime toTime
    );


    @Query("""
    SELECT COUNT(e) > 0
    FROM Event e
    WHERE LOWER(e.location) = LOWER(:location)
    AND e.fromTime < :toTime
    AND e.toTime > :fromTime
    AND e.id <> :id
""")
    boolean existsEventConflictExcludingId(
            @Param("location") String location,
            @Param("fromTime") LocalDateTime fromTime,
            @Param("toTime") LocalDateTime toTime,
            @Param("id") Long id
    );
}
