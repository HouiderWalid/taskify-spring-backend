package com.taskify.taskifyspringback.models.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.taskify.taskifyspringback.models.entities.Project;
import com.taskify.taskifyspringback.models.entities.User;
import com.taskify.taskifyspringback.models.entities.helpers.TaskPriority;
import com.taskify.taskifyspringback.models.entities.helpers.TaskStatus;
import com.taskify.taskifyspringback.validations.ExistsInDatabase;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskForm {

    @JsonProperty("title")
    @NotBlank(message = "name field is required")
    @Size(max = 255, message = "the name must have less than 255 characters")
    private String title;

    @JsonProperty("description")
    @Size(max = 5000, message = "the description must have less than 5000 characters")
    private String description;

    @JsonProperty("due_date")
    @NotNull(message = "due date field is required")
    @Future(message = "due date must be in the future")
    private LocalDateTime dueDate;

    @JsonProperty("project_id")
    @NotNull(message = "project id field is required")
    @ExistsInDatabase(entity = Project.class, message = "project with this id does not exist")
    private Long projectId;

    @JsonProperty("assigned_to_user_id")
    @NotNull(message = "assigned to user id field is required")
    @ExistsInDatabase(entity = User.class, message = "assigned user with this id does not exist")
    private Long assignedToUserId;

    @NotNull(message = "status field is required")
    private TaskStatus status;

    @NotNull(message = "priority field is required")
    private TaskPriority priority;
}
