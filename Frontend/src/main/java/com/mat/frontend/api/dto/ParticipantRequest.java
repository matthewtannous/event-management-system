package com.mat.frontend.api.dto;

public record ParticipantRequest(
        Long userId,
        Long eventId
) {
}
