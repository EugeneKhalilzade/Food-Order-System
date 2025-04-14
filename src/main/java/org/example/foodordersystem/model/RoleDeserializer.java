package org.example.foodordersystem.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.example.foodordersystem.model.entity.Role;
import org.example.foodordersystem.model.enums.RoleType;
import org.example.foodordersystem.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RoleDeserializer extends JsonDeserializer<Role> {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        try {
            String roleString = p.getValueAsString();
            if (roleString == null || roleString.isEmpty()) {
                throw new IOException("Role value cannot be empty");
            }

            // Try to find by name first
            Role role = roleRepository.findByName(roleString);

            // If not found by name, try to convert to RoleType and find by type
            if (role == null) {
                try {
                    RoleType roleType = RoleType.valueOf(roleString);
                    role = roleRepository.findByType(roleType);
                } catch (IllegalArgumentException e) {
                    // Invalid RoleType enum value
                    throw new IOException("Invalid role type: " + roleString);
                }
            }

            if (role == null) {
                throw new IOException("Role not found for value: " + roleString);
            }

            return role;
        } catch (Exception e) {
            // Log the error or handle it as needed
            throw new IOException("Error deserializing Role: " + e.getMessage(), e);
        }
    }
}