package com.taskify.taskifyspringback.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.taskify.taskifyspringback.models.entities.Project;
import com.taskify.taskifyspringback.models.entities.User;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Page<Project> findByCreator(User creator, Pageable pageable);
}
