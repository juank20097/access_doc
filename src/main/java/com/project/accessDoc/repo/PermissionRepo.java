package com.project.accessDoc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.accessDoc.entities.Permission;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@Repository
public interface PermissionRepo extends JpaRepository<Permission, Integer> {
    // Métodos adicionales personalizados si son necesarios
}
