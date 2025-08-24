package com.taskify.taskifyspringback.models.dto.project;

import java.time.LocalDateTime;

import com.taskify.taskifyspringback.models.entities.Project;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilteredProjectsDto {
    
    private Long id;
    private String name;
    private String description;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;

    public static FilteredProjectsDto fromEntity(Project project) {
        return new FilteredProjectsDto(project.getId(), project.getName(), project.getDescription(), project.getDueDate(), project.getCreatedAt());
    }
}
