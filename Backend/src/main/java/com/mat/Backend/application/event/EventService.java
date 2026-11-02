package com.mat.Backend.application.event;

import com.mat.Backend.application.exception.ConflictException;
import com.mat.Backend.application.exception.ResourceNotFoundException;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository repository;
    private final EventMapper mapper;

    public EventService(EventRepository repository, EventMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public EventResponse create(EventRequest request) {
        Event event = mapper.toEntity(request);
        if (event.getFromTime().isAfter(event.getToTime())) {
            throw new ConflictException("Start time cannot be after end time");
        }

        checkEventConflict(event);

        repository.save(event);
        return mapper.toResponse(event);
    }

    @Transactional
    public List<EventResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Transactional
    public EventResponse findById(Long id) {
        return mapper.toResponse(findEntityById(id));
    }

    @Transactional
    public EventResponse update(Long id, EventRequest request) {
        Event event = findEntityById(id);
        mapper.updateEntity(event, request);

        if (event.getFromTime().isAfter(event.getToTime())) {
            throw new ConflictException("Start time cannot be after end time");
        }

        checkEventConflictExcludingId(event, id);

        Event updatedEvent = repository.save(event);
        return mapper.toResponse(updatedEvent);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findEntityById(id));
    }

    // Other
    @Transactional
    public List<EventResponse> findAllWithUserParticipating(Long userId) {
        return repository.findEventsWithUserParticipating(userId).stream().map(mapper::toResponse).toList();
    }

    @Transactional
    public List<EventResponse> findAllWithUserOrganizing(Long userId) {
        return repository.findEventsWithUserOrganizing(userId).stream().map(mapper::toResponse).toList();
    }

    /**
     * Helper methods
     */

    private Event findEntityById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found"));
    }

    // Check if the new event does not conflict with other events
    private void checkEventConflict(Event event) {
        if (repository.existsEventConflict(event.getLocation(), event.getFromTime(), event.getToTime())) {
            throw new ConflictException("There is already an event scheduled at this location and time");
        }
    }

    // Check if the new event does not conflict with other events excluding itself
    private void checkEventConflictExcludingId(Event event, Long id) {
        if (repository.existsEventConflictExcludingId(event.getLocation(), event.getFromTime(), event.getToTime(), id)) {
            throw new ConflictException("There is already an event scheduled at this location and time");
        }
    }
}
