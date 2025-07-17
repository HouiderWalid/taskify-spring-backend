package com.taskify.taskifyspringback.services;

import com.taskify.taskifyspringback.models.entities.Role;
import com.taskify.taskifyspringback.repositories.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role create(Role role) {
        return this.roleRepository.save(role);
    }

    public Role findByName(String name) {
        return this.roleRepository.findByName(name);
    }
}
