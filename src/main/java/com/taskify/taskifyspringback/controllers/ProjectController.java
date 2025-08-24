package com.taskify.taskifyspringback.controllers;

import com.taskify.taskifyspringback.middlwares.HasPermission;
import com.taskify.taskifyspringback.models.controller.Response;
import com.taskify.taskifyspringback.models.dto.ProjectForm;
import com.taskify.taskifyspringback.models.dto.project.FilteredProjectsDto;
import com.taskify.taskifyspringback.models.entities.Permission;
import com.taskify.taskifyspringback.models.entities.Project;
import com.taskify.taskifyspringback.models.entities.User;
import com.taskify.taskifyspringback.models.implementations.TaskifyUserDetails;
import com.taskify.taskifyspringback.services.ProjectService;

import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @HasPermission(permissionName = Permission.CREATE_PROJECT)
    public Response create(
            @AuthenticationPrincipal TaskifyUserDetails userDetails,
            @RequestBody @Valid ProjectForm projectForm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int show) {

        User user = userDetails.user();
        String projectName = projectForm.getName();
        String projectDescription = projectForm.getDescription();
        LocalDateTime dueDate = projectForm.getDueDate();

        Project project = new Project();
        project.setName(projectName);
        project.setDescription(projectDescription);
        project.setDueDate(dueDate);
        project.setCreator(user);

        projectService.create(project);

        return this.getFilteredProjects(userDetails, page, show, "Project created successfully.");
    }

    @GetMapping
    @HasPermission(permissionName = Permission.VIEW_PROJECTS)
    public Response getFilteredProjects(
            @AuthenticationPrincipal TaskifyUserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int show,
            String message) {

        User user = userDetails.user();
        Page<Project> projects = projectService.getPaginatedProjects(user, page, show);
        return new Response(200, projects.map(FilteredProjectsDto::fromEntity), message);
    }

    @PutMapping("/{id}")
    @HasPermission(permissionName = Permission.UPDATE_PROJECT)
    public Response putMethodName(@AuthenticationPrincipal TaskifyUserDetails userDetails,
            @RequestBody @Valid ProjectForm projectForm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int show,
            @PathVariable String id) {

        Project project = projectService.findById(Long.parseLong(id));

        if (project == null) {
            return new Response(404, new ArrayList<>(), "Project not found.");
        }

        project.setName(projectForm.getName());
        project.setDescription(projectForm.getDescription());
        project.setDueDate(projectForm.getDueDate());

        projectService.save(project);

        return this.getFilteredProjects(userDetails, page, show, "Project updated successfully.");
    }

    @DeleteMapping("/{id}")
    @HasPermission(permissionName = Permission.DELETE_PROJECT)
    public Response deleteProject(
            @AuthenticationPrincipal TaskifyUserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int show,
            @PathVariable String id) {

        Project project = projectService.findById(Long.parseLong(id));

        if (project == null) {
            return new Response(404, new ArrayList<>(), "Project not found.");
        }

        projectService.deleteById(project.getId());
        
        return this.getFilteredProjects(userDetails, page, show, "Project deleted successfully.");
    }
}
