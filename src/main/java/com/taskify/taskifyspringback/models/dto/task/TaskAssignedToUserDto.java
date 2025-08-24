package com.taskify.taskifyspringback.models.dto.task;

import com.taskify.taskifyspringback.models.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskAssignedToUserDto {

    private Long id;
    private String email;

    public static TaskAssignedToUserDto fromEntity(User user) {
        return new TaskAssignedToUserDto(user.getId(), user.getEmail());
    }
}
