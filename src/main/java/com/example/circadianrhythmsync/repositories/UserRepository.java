package com.example.circadianrhythmsync.repositories;

import com.example.circadianrhythmsync.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRole_RoleId(Long roleId);
    Optional<User> findByUsername(String username);
    int countByUsername(String name);
}