package com.mat.Backend.application.announcement;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AnnouncementRequest(
        @NotBlank String message,
        @NotNull boolean isPublic,
        @NotNull Long userId,
        @NotNull Long eventId
) {
}