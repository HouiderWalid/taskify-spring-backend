package com.taskify.taskifyspringback.models.dto.user;

import com.taskify.taskifyspringback.models.entities.Permission;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationPermissionDto {

    private Long id;
    private String name;

    public static AuthenticationPermissionDto fromEntity(Permission permission) {
        return new AuthenticationPermissionDto(permission.getId(), permission.getName());
    }
}
