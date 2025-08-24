package com.taskify.taskifyspringback.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.taskify.taskifyspringback.models.entities.Task;
import com.taskify.taskifyspringback.models.entities.User;
import com.taskify.taskifyspringback.repositories.TaskRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TaskService {
    
        private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task create(Task task) {
        return this.taskRepository.save(task);
    }

    public Task findById(Long id) {
        return this.taskRepository.findById(id).orElse(null);
    }

    public Page<Task> getPaginatedTasks(User user, int page, int show) {
        Pageable pageable = PageRequest.of(page, show, Sort.by("createdAt").descending());
        return taskRepository.findByAssignedToUser(user, pageable);
    }

    public void save(Task task){
        this.taskRepository.save(task);
    }

    public void deleteById(Long id) {
        this.taskRepository.deleteById(id);
    }

    public Task findTaskByIdForUser(Long taskId, Long userId) {
        return this.taskRepository.findByIdAndAssignedToUserId(taskId, userId)
                .orElse(null);
    }
}
