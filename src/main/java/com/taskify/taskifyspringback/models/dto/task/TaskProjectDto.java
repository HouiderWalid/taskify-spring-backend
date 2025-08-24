package com.taskify.taskifyspringback.models.dto.task;

import java.time.LocalDateTime;

import com.taskify.taskifyspringback.models.dto.project.FilteredProjectsDto;
import com.taskify.taskifyspringback.models.entities.Project;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskProjectDto {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;

    public static TaskProjectDto fromEntity(Project project) {
        return new TaskProjectDto(project.getId(), project.getName(), project.getDescription(),
                project.getDueDate(), project.getCreatedAt());
    }
}
