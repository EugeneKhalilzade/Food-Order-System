package org.example.foodordersystem.model.dto;

import lombok.Data;
import org.example.foodordersystem.model.entity.Role;

@Data
public class AuthRequestDTO {
    private String username;
    private String email;
    private String password;
    private Role role;
}