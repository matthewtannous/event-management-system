package com.mat.Backend.application.attendant;

import com.mat.Backend.application.event.Event;
import com.mat.Backend.application.user.User;
import org.springframework.stereotype.Component;

@Component
public class AttendantMapper {
    public Attendant toEntity(AttendantRequest request, User user, Event event) {
        Attendant attendant = new Attendant();
        attendant.setUser(user);
        attendant.setEvent(event);
        return attendant;
    }

    public AttendantResponse toResponse(Attendant attendant) {
        return new AttendantResponse(
                attendant.getId(),
                attendant.getUser().getId(),
                attendant.getEvent().getId()
        );
    }

    // Request is not needed ???
    public void updateEntity(Attendant attendant, AttendantRequest request, User user, Event event) {
        attendant.setUser(user);
        attendant.setEvent(event);
    }
}
