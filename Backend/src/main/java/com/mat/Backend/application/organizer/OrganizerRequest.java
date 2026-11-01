package com.mat.Backend.application.organizer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrganizerRequest(
        @NotNull Long userId,
        @NotNull Long eventId
) {
}