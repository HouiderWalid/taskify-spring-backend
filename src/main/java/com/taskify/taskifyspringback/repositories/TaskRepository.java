package com.taskify.taskifyspringback.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.taskify.taskifyspringback.models.entities.Task;
import com.taskify.taskifyspringback.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByAssignedToUser(User assignedToUser, Pageable pageable);
    Optional<Task> findByIdAndAssignedToUserId(Long id, Long userId);
}
