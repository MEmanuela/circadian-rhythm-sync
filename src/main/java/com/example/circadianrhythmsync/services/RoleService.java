package com.example.circadianrhythmsync.services;

import com.example.circadianrhythmsync.entities.Role;
import com.example.circadianrhythmsync.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }
}
