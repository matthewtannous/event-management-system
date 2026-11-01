package com.mat.frontend.api.dto;

public record AnnouncementResponse(
        Long id,
        String message,
        boolean isPublic,
        Long userId,
        Long eventId,
        LoginResponse createdAt
) {
}
