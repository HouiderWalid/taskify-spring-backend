package com.taskify.taskifyspringback.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "permissions")
@Data
@NoArgsConstructor
public class Permission {

    public static final String VIEW_OVERVIEW = "view-overview";
    public static final String VIEW_PROJECTS = "view-projects";
    public static final String CREATE_PROJECT = "create-project";
    public static final String UPDATE_PROJECT = "update-project";
    public static final String DELETE_PROJECT = "delete-project";
    public static final String VIEW_TASKS = "view-tasks";
    public static final String CREATE_TASK = "create-task";
    public static final String UPDATE_TASK = "update-task";
    public static final String DELETE_TASK = "delete-task";
    public static final String VIEW_USERS = "view-users";
    public static final String CREATE_USER = "create-user";
    public static final String UPDATE_USER = "update-user";
    public static final String VIEW_CHAT = "view-chat";
    public static final String VIEW_SETTINGS = "view-settings";
    public static final String UPDATE_SETTINGS = "update-settings";
    public static final String UPDATE_TASK_STATUS = "update-task-status";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
}
