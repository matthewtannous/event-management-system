package com.mat.frontend.api.dto;

public record UserRequest(
        String username,
        String email,
        String firstName,
        String lastName,
        String password
) {
}
