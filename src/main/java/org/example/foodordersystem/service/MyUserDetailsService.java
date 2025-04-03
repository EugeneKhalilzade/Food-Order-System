package org.example.foodordersystem.service;

import org.example.foodordersystem.model.entity.User;
import org.example.foodordersystem.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    public MyUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = repo.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }

        User user = userOptional.get();

        // Get the role name or type for Spring Security
        String roleAuthority = user.getRole().getType().name();

        // Prefix with ROLE_ as Spring Security expects this format
        Collection<? extends GrantedAuthority> authorities =
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + roleAuthority));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}