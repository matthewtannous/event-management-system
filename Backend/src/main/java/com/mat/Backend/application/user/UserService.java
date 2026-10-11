package com.mat.Backend.application.user;

import com.mat.Backend.application.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public UserResponse create(UserRequest request) {
        User user = mapper.toEntity(request);

        repository.save(user);
        return mapper.toResponse(user);
    }

    @Transactional
    public List<UserResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Transactional
    public UserResponse findById(Long id) {
        return mapper.toResponse(findEntityById(id));
    }

    @Transactional
    public UserResponse update(Long id, UserRequest request) {
        User user = findEntityById(id);
        mapper.updateEntity(user, request);

        User updatedUser = repository.save(user);

        return mapper.toResponse(updatedUser);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findEntityById(id));
    }

    /**
     * Helper methods
     */

    private User findEntityById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
