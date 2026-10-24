package com.mat.Backend.application.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistrationRequest(@NotBlank String username, @NotBlank @Size(min = 8) String password,
                                  @NotBlank @Email String email, @NotBlank String firstName,
                                  @NotBlank String lastName) {
}
