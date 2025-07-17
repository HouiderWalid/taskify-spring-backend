package com.taskify.taskifyspringback.repositories;

import com.taskify.taskifyspringback.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    public User findByEmail(String email);
}
