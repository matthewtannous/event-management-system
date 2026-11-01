package com.mat.frontend.api.dto;

public record ParticipantResponse(
        Long id,
        Long userId,
        Long eventId
) {
}
