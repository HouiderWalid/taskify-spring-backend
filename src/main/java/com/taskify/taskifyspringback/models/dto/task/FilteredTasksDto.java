package com.taskify.taskifyspringback.models.dto.task;

import java.time.LocalDateTime;

import com.taskify.taskifyspringback.models.entities.Task;
import com.taskify.taskifyspringback.models.entities.helpers.TaskPriority;
import com.taskify.taskifyspringback.models.entities.helpers.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilteredTasksDto {

    private Long id;

    private TaskProjectDto project;
    private TaskAssignedToUserDto user;

    private String title;
    private String description;
    private LocalDateTime dueDate;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime createdAt;

    public static FilteredTasksDto fromEntity(Task task) {
        return new FilteredTasksDto(
                task.getId(),
                task.getProject() != null ? TaskProjectDto.fromEntity(task.getProject()) : null,
                task.getAssignedToUser() != null ? TaskAssignedToUserDto.fromEntity(task.getAssignedToUser()) : null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getStatus(),
                task.getPriority(),
                task.getCreatedAt());
    }
}
