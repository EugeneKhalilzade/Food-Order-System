package org.example.foodordersystem.model.dto;

import lombok.Data;

@Data
public class AuthRequestDTO {
    private String username;
    private String email;
    private String password;
    private String role;
}