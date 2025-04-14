package org.example.foodordersystem.repository;

import org.example.foodordersystem.model.entity.Role;
import org.example.foodordersystem.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
    Role findByType(RoleType type);

}