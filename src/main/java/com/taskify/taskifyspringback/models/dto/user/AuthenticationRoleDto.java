package com.taskify.taskifyspringback.models.dto.user;

import com.taskify.taskifyspringback.models.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationRoleDto {
    
    private Long id;
    private String name;

    public static AuthenticationRoleDto fromEntity(Role role) {
        return new AuthenticationRoleDto(role.getId(), role.getName());
    }
}
