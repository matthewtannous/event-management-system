package com.mat.Backend.application.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT p.event FROM Participant p WHERE p.user.id = :userId")
    List<Event> findEventsWithUserParticipating(@Param("userId") Long userId);

    @Query("SELECT o.event FROM Organizer o WHERE o.user.id = :userId")
    List<Event> findEventsWithUserOrganizing(@Param("userId") Long userId);

}
