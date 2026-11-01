package com.mat.frontend.api.dto;

public record AnnouncementRequest(
        String message,
        boolean isPublic,
        Long userId,
        Long eventId
) {
}
