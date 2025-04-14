package org.example.foodordersystem.config;

import org.example.foodordersystem.model.entity.Role;
import org.example.foodordersystem.model.enums.RoleType;
import org.example.foodordersystem.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if roles exist, if not, create them
        if (roleRepository.count() == 0) {
            Role customerRole = new Role();
            customerRole.setName("customer_role");
            customerRole.setType(RoleType.CUSTOMER);
            roleRepository.save(customerRole);

            Role staffRole = new Role();
            staffRole.setName("staff_role");
            staffRole.setType(RoleType.STAFF);
            roleRepository.save(staffRole);

            Role adminRole = new Role();
            adminRole.setName("admin_role");
            adminRole.setType(RoleType.ADMIN);
            roleRepository.save(adminRole);

            System.out.println("Roles created successfully");
        }
    }
}