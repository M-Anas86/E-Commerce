package com.example.E_Commerce.services;

import com.example.E_Commerce.entity.Role;
import com.example.E_Commerce.entity.User;
import com.example.E_Commerce.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public AdminInitializer(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void run(String @NonNull ... args) {
        boolean adminExists = userRepository.findAll().stream()
                .anyMatch(user -> user.getRole() == Role.ADMIN);

        if(!adminExists){
            User masterAdmin = new User();
            masterAdmin.setUsername("superAdmin");
            masterAdmin.setEmail("admin@marketplace.com");
            masterAdmin.setPassword("admin123");
            masterAdmin.setRole(Role.ADMIN);

            userRepository.save(masterAdmin);
            System.out.println("System notice: master admin initialized successfully");
        }
    }
}
