package com.project.ordermanagementsystems.repository;

import com.project.ordermanagementsystems.model.ERole;
import com.project.ordermanagementsystems.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(ERole name);
}
