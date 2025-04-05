package org.example.foodordersystem.service.impl;

import org.example.foodordersystem.model.dto.UserDTO;
import org.example.foodordersystem.repository.UserRepository;
import org.example.foodordersystem.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserDTO.class));
    }

    @Override
    public Optional<UserDTO> getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> modelMapper.map(user, UserDTO.class));
    }


    @Override
    public Optional<UserDTO> updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id).map(existingUser -> {
            if (!existingUser.getUsername().equals(userDTO.getUsername()) &&
                    userRepository.existsByUsername(userDTO.getUsername())) {
                throw new RuntimeException("Username already exists");
            }

            if (!existingUser.getEmail().equals(userDTO.getEmail()) &&
                    userRepository.existsByEmail(userDTO.getEmail())) {
                throw new RuntimeException("Email already exists");
            }

            modelMapper.map(userDTO, existingUser);

            if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }

            return modelMapper.map(userRepository.save(existingUser), UserDTO.class);
        });
    }

    @Override
    public boolean deleteUser(Long id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElse(false);
    }
}