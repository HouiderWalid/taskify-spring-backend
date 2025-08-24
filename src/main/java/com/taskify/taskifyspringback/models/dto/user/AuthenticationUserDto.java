package com.taskify.taskifyspringback.models.dto.user;

import java.util.Set;

import com.taskify.taskifyspringback.models.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationUserDto {

    private Long id;
    private String email;
    private String fullName;
    private AuthenticationRoleDto role;
    private Set<AuthenticationPermissionDto> permissions;

    public static AuthenticationUserDto fromEntity(User user) {
        return new AuthenticationUserDto(user.getId(), user.getEmail(), user.getFullName(),
                user.getRole() != null ? AuthenticationRoleDto.fromEntity(user.getRole()) : null,
                user.getPermissions() != null
                        ? user.getPermissions().stream().map(AuthenticationPermissionDto::fromEntity)
                                .collect(java.util.stream.Collectors.toSet())
                        : Set.of());
    }
}
