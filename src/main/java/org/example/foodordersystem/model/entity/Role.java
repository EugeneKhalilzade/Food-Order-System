package org.example.foodordersystem.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.foodordersystem.model.enums.RoleType;

import java.util.List;

@Entity
@Data
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType type;

    @OneToMany(mappedBy = "role")
    private List<User> users;
}