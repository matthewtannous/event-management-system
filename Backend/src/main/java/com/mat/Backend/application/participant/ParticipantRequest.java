package com.mat.Backend.application.participant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ParticipantRequest(
        @NotNull Long userId,
        @NotNull Long eventId
) {
}