package org.example.foodordersystem.repository;

import org.example.foodordersystem.model.entity.Role;
import org.example.foodordersystem.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByType(RoleType type);
}