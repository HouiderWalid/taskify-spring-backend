package com.taskify.taskifyspringback.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class Role {

    public static final String ADMIN_ROLE = "admin";
    public static final String MANAGER_ROLE = "manager";
    public static final String MEMBER_ROLE = "member";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
}
