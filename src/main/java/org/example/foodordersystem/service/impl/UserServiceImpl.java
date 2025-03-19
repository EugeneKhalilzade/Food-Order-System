package org.example.foodordersystem.service.impl;

import org.example.foodordersystem.model.dto.UserDTO;
import org.example.foodordersystem.model.entity.Role;
import org.example.foodordersystem.model.entity.User;
import org.example.foodordersystem.repository.RoleRepository;
import org.example.foodordersystem.repository.UserRepository;
import org.example.foodordersystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public Optional<UserDTO> getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(this::convertToDTO);
    }

    @Override
    public UserDTO registerUser(UserDTO userDTO, String roleName) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRole(role);

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    @Override
    public Optional<UserDTO> updateUser(Long id, UserDTO userDTO) {
        if (!userRepository.existsById(id)) {
            return Optional.empty();
        }

        Optional<User> existingUserByUsername = userRepository.findByUsername(userDTO.getUsername());
        if (existingUserByUsername.isPresent() && !existingUserByUsername.get().getId().equals(id)) {
            throw new RuntimeException("Username already exists");
        }

        Optional<User> existingUserByEmail = userRepository.findByEmail(userDTO.getEmail());
        if (existingUserByEmail.isPresent() && !existingUserByEmail.get().getId().equals(id)) {
            throw new RuntimeException("Email already exists");
        }

        User user = userRepository.findById(id).get();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        return Optional.of(convertToDTO(updatedUser));
    }

    @Override
    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            return false;
        }

        userRepository.deleteById(id);
        return true;
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().getName());
        // Don't set password in DTO for security reasons
        return dto;
    }
}