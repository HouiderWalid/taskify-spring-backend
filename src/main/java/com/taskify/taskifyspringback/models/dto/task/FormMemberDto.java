package com.taskify.taskifyspringback.models.dto.task;

import com.taskify.taskifyspringback.models.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FormMemberDto {
    
    private Long id;
    private String fullName;

    public static FormMemberDto fromEntity(User user) {
        return new FormMemberDto(user.getId(), user.getFullName());
    }
}
