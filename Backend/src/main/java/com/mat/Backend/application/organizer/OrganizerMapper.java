package com.mat.Backend.application.organizer;

import com.mat.Backend.application.event.Event;
import com.mat.Backend.application.user.User;
import org.springframework.stereotype.Component;

@Component
public class OrganizerMapper {
    public Organizer toEntity(OrganizerRequest request, User user, Event event) {
        Organizer organizer = new Organizer();
        organizer.setUser(user);
        organizer.setEvent(event);
        return organizer;
    }

    public OrganizerResponse toResponse(Organizer organizer) {
        return new OrganizerResponse(
                organizer.getId(),
                organizer.getUser().getId(),
                organizer.getEvent().getId()
        );
    }

    // Request is not needed ???
    public void updateEntity(Organizer organizer, OrganizerRequest request, User user, Event event) {
        organizer.setUser(user);
        organizer.setEvent(event);
    }
}
