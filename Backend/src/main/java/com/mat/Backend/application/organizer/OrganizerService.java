package com.mat.Backend.application.organizer;

import com.mat.Backend.application.event.Event;
import com.mat.Backend.application.event.EventRepository;
import com.mat.Backend.application.exception.ResourceNotFoundException;
import com.mat.Backend.application.user.User;
import com.mat.Backend.application.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizerService {
    private final OrganizerRepository repository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final OrganizerMapper mapper;

    public OrganizerService(OrganizerRepository repository, UserRepository userRepository, EventRepository eventRepository, OrganizerMapper mapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.mapper = mapper;
    }

    @Transactional
    public OrganizerResponse create(OrganizerRequest request) {
        User user = userRepository.findById(request.userId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Event event = eventRepository.findById(request.eventId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Organizer organizer = mapper.toEntity(request, user, event);
        return mapper.toResponse(repository.save(organizer));
    }

    @Transactional
    public List<OrganizerResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Transactional
    public OrganizerResponse findById(Long id) {
        return mapper.toResponse(findEntity(id));
    }

//    Should not exist. User cannot "change" to organize another event. He must be removed from an event and added to a new one
//    @Transactional
//    public OrganizerResponse update(Long id, OrganizerRequest request) {
//        Organizer organizer = findEntity(id);
//        User user = userRepository.findById(request.userId())
//                .orElseThrow(
//                        () -> new ResourceNotFoundException("User not found")
//                );
//        Event event = eventRepository
//                .findById(request.eventId())
//                .orElseThrow(
//                        () -> new ResourceNotFoundException("Event not found")
//                );
//        mapper.updateEntity(organizer, request, user, event);
//        Organizer updatedOrganizer = repository.save(organizer);
//
//        return mapper.toResponse(updatedOrganizer);
//    }

    @Transactional
    public void delete(Long id) {
        Organizer organizer = findEntity(id);

        // Remove one user from the event
        Event event = organizer.getEvent();
        event.setRegistered(event.getRegistered() - 1);

        repository.delete(organizer);
    }

    private Organizer findEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Organizer not found"));
    }

    @Transactional
    // Check if a user participates in an event
    boolean userParticipatesInEvent(Long userId, Long eventId) {
        return repository.existsByUserIdAndEventId(userId, eventId);
    }
}
