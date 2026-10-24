package com.mat.frontend.api.dto;

public record UserResponse(Long id, String username, String email, String firstName, String lastName) {
}
