package org.example.foodordersystem.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthResponseDTO {
    @NotNull(message = "Username must not be null")
    private String username;
    @NotNull(message = "password must not be null")
    private String password;
}