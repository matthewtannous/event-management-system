package com.mat.Backend.application.announcement;

import com.mat.Backend.application.event.Event;
import com.mat.Backend.application.event.EventRepository;
import com.mat.Backend.application.exception.ResourceNotFoundException;
import com.mat.Backend.application.user.User;
import com.mat.Backend.application.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementService {
    private final AnnouncementRepository repository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final AnnouncementMapper mapper;

    public AnnouncementService(AnnouncementRepository repository, UserRepository userRepository, EventRepository eventRepository, AnnouncementMapper mapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.mapper = mapper;
    }

    @Transactional
    public AnnouncementResponse create(AnnouncementRequest request) {
        User user = userRepository
                .findById(request.userId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not found")
                );
        Event event = eventRepository
                .findById(request.eventId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Event not found")
                );

        Announcement announcement = mapper.toEntity(request, user, event);
        return mapper.toResponse(repository.save(announcement));
    }

    @Transactional
    public List<AnnouncementResponse> findAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional
    public AnnouncementResponse findById(Long id) {
        return mapper.toResponse(findEntity(id));
    }

    @Transactional
    public AnnouncementResponse update(Long id, AnnouncementRequest request) {
        Announcement announcement = findEntity(id);
        User user = userRepository.findById(request.userId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not found")
                );
        Event event = eventRepository
                .findById(request.eventId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Event not found")
                );

        mapper.updateEntity(announcement, request, user, event);
        Announcement updatedAnnouncement = repository.save(announcement);

        return mapper.toResponse(updatedAnnouncement);
    }

    @Transactional
    public void delete(Long id) {
        Announcement announcement = findEntity(id);
        repository.delete(announcement);
    }

    private Announcement findEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Announcement not found")
                );
    }

    // Find all announcements for an event
    List<AnnouncementResponse> findAllByEventId(Long eventId, Boolean onlyPublic) {
        if (onlyPublic) {
            // Only return public announcements for event
            return repository.findByEventIdAndIsPublicOrderByCreatedAtDesc(eventId, true).stream().map(mapper::toResponse).toList();
        }
        // Return all announcements for event
        return repository.findByEventIdOrderByCreatedAtDesc(eventId).stream().map(mapper::toResponse).toList();
    }
}
