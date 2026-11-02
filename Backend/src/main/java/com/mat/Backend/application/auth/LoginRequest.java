package com.mat.Backend.application.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Username cannot be empty") String username,
        @NotBlank(message = "Password name cannot be empty") String password) {
}
