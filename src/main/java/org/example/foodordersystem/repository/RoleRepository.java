package org.example.foodordersystem.repository;

import org.example.foodordersystem.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
//Burada deyisiklik var
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<RoleRepository> findByName(String name);
}