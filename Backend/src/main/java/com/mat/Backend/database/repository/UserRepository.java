package com.mat.Backend.database.repository;

import com.mat.Backend.database.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
