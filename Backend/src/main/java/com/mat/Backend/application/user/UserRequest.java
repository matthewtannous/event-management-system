package com.mat.Backend.application.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(
//        @NotBlank
//        Long id,
        @NotBlank
        String username,

        String firstName,
        String lastName,

        @Email
        String email,
        @NotBlank
        String password
) {
}