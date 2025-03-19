package org.example.foodordersystem.service;

import org.example.foodordersystem.model.dto.UserDTO;
import org.example.foodordersystem.model.entity.Role;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    UserDTO findByUsername(String username);

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(Long id, UserDTO userDTO);

    UserDTO updateUserRole(Long id, Role role);

    boolean deleteUser(Long id);
}