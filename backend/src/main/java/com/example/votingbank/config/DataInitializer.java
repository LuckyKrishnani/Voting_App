package com.example.votingbank.config;

import com.example.votingbank.model.Roles;
import com.example.votingbank.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize roles if they don't exist
        if (roleRepository.findByName("USER") == null) {
            Roles userRole = new Roles();
            userRole.setId((short) 1);
            userRole.setName("USER");
            roleRepository.save(userRole);
            System.out.println("Created USER role");
        }

        if (roleRepository.findByName("ADMIN") == null) {
            Roles adminRole = new Roles();
            adminRole.setId((short) 2);
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);
            System.out.println("Created ADMIN role");
        }
    }
}
