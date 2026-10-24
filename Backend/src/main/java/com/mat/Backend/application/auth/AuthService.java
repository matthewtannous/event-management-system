package com.mat.Backend.application.auth;

import com.mat.Backend.application.exception.ConflictException;
import com.mat.Backend.application.exception.ResourceNotFoundException;
import com.mat.Backend.application.user.User;
import com.mat.Backend.application.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository repository) {
        this.repository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = repository.findByUsername(loginRequest.username()).orElseThrow(() -> new ResourceNotFoundException("Invalid Usernmame or Password"));

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new ResourceNotFoundException("Invalid Usernmame or Password");
        }

        return new LoginResponse(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail());
    }

    @Transactional
    public RegistrationResponse register(RegistrationRequest registrationRequest) {
        if (repository.existsByUsername(registrationRequest.username().trim())) {
            throw new ConflictException("Username is already taken");
        }

        User user = new User();
        user.setUsername(registrationRequest.username().trim());
        user.setFirstName(registrationRequest.firstName());
        user.setLastName(registrationRequest.lastName());
        user.setEmail(registrationRequest.email());
        user.setPassword(passwordEncoder.encode(registrationRequest.password()));

        User saved = repository.save(user);

        return new RegistrationResponse(saved.getId(), saved.getUsername(), saved.getFirstName(), saved.getLastName(), saved.getEmail());
    }
}
