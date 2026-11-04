package com.mat.Backend.application.announcement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    // Return all the announcements made for an event
    List<Announcement> findByEventIdOrderByCreatedAtDesc(Long eventId);

    // Will only be called with isPublic = true, since only returning the private announcements and not the public ones is useless
    List<Announcement> findByEventIdAndIsPublicOrderByCreatedAtDesc(Long eventId, Boolean isPublic);
}
