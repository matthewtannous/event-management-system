package com.mat.frontend.api.dto;

public record RegistrationRequest(String username, String password, String email, String firstName, String lastName) {
}
