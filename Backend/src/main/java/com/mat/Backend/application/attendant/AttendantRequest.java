package com.mat.Backend.application.attendant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AttendantRequest(
        @NotNull Long userId,
        @NotNull Long eventId
) {
}