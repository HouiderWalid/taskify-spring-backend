package com.taskify.taskifyspringback.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taskify.taskifyspringback.middlwares.HasPermission;
import com.taskify.taskifyspringback.models.controller.Response;
import com.taskify.taskifyspringback.models.dto.TaskForm;
import com.taskify.taskifyspringback.models.dto.task.FilteredTasksDto;
import com.taskify.taskifyspringback.models.dto.task.FormMemberDto;
import com.taskify.taskifyspringback.models.dto.task.FormProjectsDto;
import com.taskify.taskifyspringback.models.entities.Permission;
import com.taskify.taskifyspringback.models.entities.Project;
import com.taskify.taskifyspringback.models.entities.Role;
import com.taskify.taskifyspringback.models.entities.Task;
import com.taskify.taskifyspringback.models.entities.User;
import com.taskify.taskifyspringback.models.entities.helpers.TaskPriority;
import com.taskify.taskifyspringback.models.entities.helpers.TaskStatus;
import com.taskify.taskifyspringback.models.implementations.TaskifyUserDetails;
import com.taskify.taskifyspringback.services.ProjectService;
import com.taskify.taskifyspringback.services.TaskService;
import com.taskify.taskifyspringback.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    TaskService taskService;
    ProjectService projectService;
    UserService userService;

    public TaskController(TaskService taskService, ProjectService projectService, UserService userService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping
    @HasPermission(permissionName = Permission.VIEW_TASKS)
    public Response getFilteredTasks(
            @AuthenticationPrincipal TaskifyUserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int show,
            String message) {

        User user = userDetails.user();
        Page<Task> tasks = taskService.getPaginatedTasks(user, page, show);

        return new Response(200, tasks.map(FilteredTasksDto::fromEntity), message);
    }

    @PostMapping
    @HasPermission(permissionName = Permission.CREATE_TASK)
    public Response create(
            @AuthenticationPrincipal TaskifyUserDetails userDetails,
            @RequestBody @Valid TaskForm taskForm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int show) {

        String title = taskForm.getTitle();
        String description = taskForm.getDescription();
        LocalDateTime dueDate = taskForm.getDueDate();
        TaskPriority priority = taskForm.getPriority();
        TaskStatus status = taskForm.getStatus();
        Long projectId = taskForm.getProjectId();
        Long assignedToUserId = taskForm.getAssignedToUserId();

        Project project = projectService.findById(projectId);
        if (project == null) {
            return new Response(404, new ArrayList<>(), "Project not found.");
        }

        User assignedToUser = userService.findById(assignedToUserId);
        if (assignedToUser == null) {
            return new Response(404, new ArrayList<>(), "Assigned user not found.");
        }

        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setDueDate(dueDate);
        task.setPriority(priority);
        task.setStatus(status);
        task.setProject(project);
        task.setAssignedToUser(assignedToUser);

        taskService.create(task);

        return this.getFilteredTasks(userDetails, page, show, "Task created successfully.");
    }

    @PutMapping("/{id}")
    @HasPermission(permissionName = Permission.UPDATE_TASK)
    public Response update(
            @AuthenticationPrincipal TaskifyUserDetails userDetails,
            @RequestBody @Valid TaskForm taskForm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int show,
            @PathVariable String id) {

        Task task = taskService.findById(Long.parseLong(id));

        if (task == null) {
            return new Response(404, new ArrayList<>(), "Task not found.");
        }

        Project project = projectService.findById(taskForm.getProjectId());

        if (project == null) {
            return new Response(404, new ArrayList<>(), "Project not found.");
        }

        User assignedToUser = userService.findById(taskForm.getAssignedToUserId());

        if (assignedToUser == null) {
            return new Response(404, new ArrayList<>(), "Assigned user not found.");
        }

        task.setTitle(taskForm.getTitle());
        task.setDescription(taskForm.getDescription());
        task.setDueDate(taskForm.getDueDate());
        task.setPriority(taskForm.getPriority());
        task.setStatus(taskForm.getStatus());
        task.setProject(project);
        task.setAssignedToUser(assignedToUser);

        taskService.save(task);

        return this.getFilteredTasks(userDetails, page, show, "Task updated successfully.");
    }

    @DeleteMapping("/{id}")
    @HasPermission(permissionName = Permission.DELETE_TASK)
    public Response delete(
            @AuthenticationPrincipal TaskifyUserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int show,
            @PathVariable String id) {

        Task task = taskService.findById(Long.parseLong(id));

        if (task == null) {
            return new Response(404, new ArrayList<>(), "Task not found.");
        }

        taskService.deleteById(task.getId());

        return this.getFilteredTasks(userDetails, page, show, "Task deleted successfully.");
    }

    @GetMapping("/form/projects")
    @HasPermission(permissionName = Permission.VIEW_TASKS)
    public Response getFormProjects() {
        List<Project> projects = projectService.findAll();
        return new Response(200, projects.stream().map(FormProjectsDto::fromEntity).toList(), "");
    }

    @GetMapping("/form/members")
    @HasPermission(permissionName = Permission.VIEW_TASKS)
    public Response getFormMembers() {
        List<User> users = userService.findAllByRoleName(Role.MEMBER_ROLE);
        return new Response(200, users.stream().map(FormMemberDto::fromEntity).toList(), "");
    }

}