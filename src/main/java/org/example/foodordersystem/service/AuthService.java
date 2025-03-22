package org.example.foodordersystem.service;

import lombok.RequiredArgsConstructor;
import org.example.foodordersystem.model.dto.AuthRequestDTO;
import org.example.foodordersystem.model.dto.AuthResponseDTO;
import org.example.foodordersystem.model.entity.User;
import org.example.foodordersystem.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
//bu classda deyisiklik var
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final MyUserDetailsService myUserDetailsService;


    public String register(AuthRequestDTO authRequestDTO) {
        if (userRepository.existsByUsername(authRequestDTO.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(authRequestDTO.getUsername());
        user.setEmail(authRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(authRequestDTO.getPassword()));
        user.setRole(authRequestDTO.getRole());
        userRepository.save(user);
        return "User registered successfully!";
    }

    //Burda deyisiklik olunub
    public String login(AuthResponseDTO authRequestDTO) {
        User user = userRepository.findByUsername(authRequestDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(authRequestDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        UserDetails userDetails = myUserDetailsService.loadUserByUsername(user.getUsername());

        return jwtService.generateToken(userDetails.getUsername());
    }
}