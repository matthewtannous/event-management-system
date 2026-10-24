package com.mat.Backend.application.auth;

public record LoginResponse(Long id, String username, String firstName, String lastName, String email) {
}
