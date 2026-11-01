package com.mat.frontend.api.dto;

public record OrganizerRequest(
        Long userId,
        Long eventId
) {
}
