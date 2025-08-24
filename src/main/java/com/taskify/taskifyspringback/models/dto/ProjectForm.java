package com.taskify.taskifyspringback.models.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProjectForm {
    
    @JsonProperty("name")
    @NotBlank(message = "name field is required")
    @Size(max = 255, message = "the name must have less than 255 characters")
    private String name;

    @JsonProperty("description")
    @Size(max = 5000, message = "the description must have less than 5000 characters")
    private String description;

    @JsonProperty("due_date")
    @NotNull(message = "due date field is required")
    @Future(message = "due date must be in the future")
    private LocalDateTime dueDate;

}
