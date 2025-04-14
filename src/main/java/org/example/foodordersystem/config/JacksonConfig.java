package org.example.foodordersystem.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.example.foodordersystem.model.RoleDeserializer;
import org.example.foodordersystem.model.entity.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    @Bean
    public Module roleDeserializerModule(RoleDeserializer roleDeserializer) {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Role.class, roleDeserializer);
        return module;
    }
}