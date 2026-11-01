package com.mat.Backend.application.participant;

import com.mat.Backend.application.event.Event;
import com.mat.Backend.application.event.EventRepository;
import com.mat.Backend.application.event.EventService;
import com.mat.Backend.application.exception.ConflictException;
import com.mat.Backend.application.exception.ResourceNotFoundException;
import com.mat.Backend.application.user.User;
import com.mat.Backend.application.user.UserRepository;
import com.mat.Backend.application.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantService {
    private final ParticipantRepository repository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final ParticipantMapper mapper;

    public ParticipantService(ParticipantRepository repository, UserRepository userRepository, EventRepository eventRepository, ParticipantMapper mapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.mapper = mapper;
    }

    @Transactional
    public ParticipantResponse create(ParticipantRequest request) {
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
        if (event.getCapacity() >= event.getRegistered()) {
            throw new ConflictException("Event is full. You can't register");
        }
        // Add one person to the event registration
        event.setRegistered(event.getRegistered() + 1);

        Participant participant = mapper.toEntity(request, user, event);
        return mapper.toResponse(repository.save(participant));
    }

    @Transactional
    public List<ParticipantResponse> findAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional
    public ParticipantResponse findById(Long id) {
        return mapper.toResponse(findEntity(id));
    }

//    Should not exist. User cannot "change" his registration. He must unregister from an event and register to a new one
//    @Transactional
//    public ParticipantResponse update(Long id, ParticipantRequest request) {
//        Participant participant = findEntity(id);
//        User user = userRepository.findById(request.userId())
//                .orElseThrow(
//                        () -> new ResourceNotFoundException("User not found")
//                );
//        Event event = eventRepository
//                .findById(request.eventId())
//                .orElseThrow(
//                        () -> new ResourceNotFoundException("Event not found")
//                );
//
//        mapper.updateEntity(participant, request, user, event);
//        Participant updatedParticipant = repository.save(participant);
//
//        return mapper.toResponse(updatedParticipant);
//    }

    @Transactional
    public void delete(Long id) {
        Participant participant = findEntity(id);
        // Add one person to the event registration
        Event event = participant.getEvent();
        event.setRegistered(event.getRegistered() - 1);

        repository.delete(participant);
    }

    private Participant findEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Participant not found")
                );
    }
}
