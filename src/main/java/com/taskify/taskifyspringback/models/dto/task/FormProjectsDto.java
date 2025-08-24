package com.taskify.taskifyspringback.models.dto.task;

import com.taskify.taskifyspringback.models.entities.Project;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FormProjectsDto {
    
    private Long id;
    private String name;

    public static FormProjectsDto fromEntity(Project project) {
        return new FormProjectsDto(project.getId(), project.getName());
    }
}
