package com.mat.Backend.application.announcement;

import java.time.LocalDateTime;

public record AnnouncementResponse(
        Long id,
        String message,
        boolean isPublic,
        Long userId,
        Long eventId,
        LocalDateTime createdAt
) {
}
