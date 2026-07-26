package com.example.E_Commerce.services;

import com.example.E_Commerce.dto.ProductDto;
import com.example.E_Commerce.dto.UserDto;
import com.example.E_Commerce.entity.Order;
import com.example.E_Commerce.entity.Products;
import com.example.E_Commerce.entity.Role;
import com.example.E_Commerce.entity.User;
import com.example.E_Commerce.repository.OrderRepository;
import com.example.E_Commerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public UserService(UserRepository userRepository, OrderRepository orderRepository ) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public User registerUser(User user) {
        // Double check that 'user.getEmail()' is written in lowercase exactly like this
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already registered!");
        }

        if(user.getRole() == Role.ADMIN){
            boolean adminExist = userRepository.findAll().stream()
                    .anyMatch(u -> u.getRole() == Role.ADMIN);

            if(adminExist){
                throw new RuntimeException("Access Denial: An Administration account already exists in this platform");
            }
        }

        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    public Optional<User> getUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }
}