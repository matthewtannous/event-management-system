package com.mat.Backend.application.announcement;

import com.mat.Backend.application.event.Event;
import com.mat.Backend.application.user.User;
import org.springframework.stereotype.Component;

@Component
public class AnnouncementMapper {
    public Announcement toEntity(AnnouncementRequest request, User user, Event event) {
        Announcement announcement = new Announcement();
        announcement.setMessage(request.message());
        announcement.setPublic(request.isPublic());
        announcement.setUser(user);
        announcement.setEvent(event);
        return announcement;
    }

    public AnnouncementResponse toResponse(Announcement announcement) {
        return new AnnouncementResponse(
                announcement.getId(),
                announcement.getMessage(),
                announcement.isPublic(),
                announcement.getUser().getId(),
                announcement.getEvent().getId(),
                announcement.getCreatedAt()
        );
    }

    public void updateEntity(Announcement announcement, AnnouncementRequest request, User user, Event event) {
        announcement.setMessage(request.message());
        announcement.setPublic(request.isPublic());
        announcement.setUser(user);
        announcement.setEvent(event);
    }
}
