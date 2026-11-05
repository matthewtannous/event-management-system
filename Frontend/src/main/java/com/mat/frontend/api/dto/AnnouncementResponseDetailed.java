package com.mat.frontend.api.dto;

import java.time.LocalDateTime;

public record AnnouncementResponseDetailed(
        Long id,
        String message,
        boolean isPublic,
        Long userId,
        Long eventId,
        LocalDateTime createdAt,
        String username
) {
}
