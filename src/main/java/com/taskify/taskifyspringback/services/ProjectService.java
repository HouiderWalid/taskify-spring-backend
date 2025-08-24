package com.taskify.taskifyspringback.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.taskify.taskifyspringback.models.entities.Project;
import com.taskify.taskifyspringback.models.entities.User;
import com.taskify.taskifyspringback.repositories.ProjectRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project create(Project project) {
        return this.projectRepository.save(project);
    }

    public Project findById(Long id) {
        return this.projectRepository.findById(id).orElse(null);
    }

    public Page<Project> getPaginatedProjects(User user, int page, int show) {
        Pageable pageable = PageRequest.of(page, show, Sort.by("createdAt").descending());
        return projectRepository.findByCreator(user, pageable);
    }

    public List<Project> findAll() {
        return this.projectRepository.findAll();
    }

    public void save(Project project){
        this.projectRepository.save(project);
    }

    public void deleteById(Long id) {
        this.projectRepository.deleteById(id);
    }
}
