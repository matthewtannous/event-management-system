package com.mat.Backend.application.attendant;

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
public class AttendantService {
    private final AttendantRepository repository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final AttendantMapper mapper;

    public AttendantService(AttendantRepository repository, UserRepository userRepository, EventRepository eventRepository, AttendantMapper mapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.mapper = mapper;
    }

    @Transactional
    public AttendantResponse create(AttendantRequest request) {
        User user = userRepository
                .findById(request.userId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not found")
                );
        Event event = eventRepository
                .findById(request.eventId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not found")
                );
        if (event.getCapacity() >= event.getRegistered()) {
            throw new ConflictException("Event is full. You can't register");
        }
        // Add one person to the event registration
        event.setRegistered(event.getRegistered() + 1);

        Attendant attendant = mapper.toEntity(request, user, event);
        return mapper.toResponse(repository.save(attendant));
    }

    @Transactional
    public List<AttendantResponse> findAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional
    public AttendantResponse findById(Long id) {
        return mapper.toResponse(findEntity(id));
    }

//    Should not exist. User cannot "change" his registration. He must unregister from an event and register to a new one
//    @Transactional
//    public AttendantResponse update(Long id, AttendantRequest request) {
//        Attendant attendant = findEntity(id);
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
//        mapper.updateEntity(attendant, request, user, event);
//        Attendant updatedAttendant = repository.save(attendant);
//
//        return mapper.toResponse(updatedAttendant);
//    }

    @Transactional
    public void delete(Long id) {
        Attendant attendant = findEntity(id);
        repository.delete(attendant);
    }

    private Attendant findEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Attendant not found")
                );
    }
}
