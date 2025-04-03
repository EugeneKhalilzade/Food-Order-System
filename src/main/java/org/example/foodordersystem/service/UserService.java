package org.example.foodordersystem.service;

import org.example.foodordersystem.model.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getAllUsers();
    Optional<UserDTO> getUserById(Long id);
    Optional<UserDTO> getUserByUsername(String username);
    Optional<UserDTO> updateUser(Long id, UserDTO userDTO);
    boolean deleteUser(Long id);
}
