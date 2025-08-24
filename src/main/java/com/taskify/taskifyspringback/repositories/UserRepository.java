package com.taskify.taskifyspringback.repositories;

import com.taskify.taskifyspringback.models.entities.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);
    List<User> findAllByRole_Name(String roleName);
}
