package com.mat.Backend.application.participant;

import jakarta.validation.constraints.NotNull;

public record ParticipantRequest(
        @NotNull Long userId,
        @NotNull Long eventId
) {
}