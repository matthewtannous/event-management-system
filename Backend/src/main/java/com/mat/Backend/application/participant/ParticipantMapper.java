package com.mat.Backend.application.participant;

import com.mat.Backend.application.event.Event;
import com.mat.Backend.application.user.User;
import org.springframework.stereotype.Component;

@Component
public class ParticipantMapper {
    public Participant toEntity(ParticipantRequest request, User user, Event event) {
        Participant participant = new Participant();
        participant.setUser(user);
        participant.setEvent(event);
        return participant;
    }

    public ParticipantResponse toResponse(Participant participant) {
        return new ParticipantResponse(
                participant.getId(),
                participant.getUser().getId(),
                participant.getEvent().getId()
        );
    }

    // Request is not needed ???
    public void updateEntity(Participant participant, ParticipantRequest request, User user, Event event) {
        participant.setUser(user);
        participant.setEvent(event);
    }
}
