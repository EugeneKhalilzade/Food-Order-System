package org.example.foodordersystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleRepository, Long> {
    Optional<RoleRepository> findByName(String name);
}