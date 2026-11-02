package com.mat.Backend.application.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistrationRequest(
        @NotBlank(message = "User name cannot be empty") String username,
        @NotBlank @Size(min = 8, message = "Password must contain 8 or more characters") String password,
        @NotBlank @Email(message = "Invalid email address") String email,
        @NotBlank String firstName,
        @NotBlank String lastName
) {
}
