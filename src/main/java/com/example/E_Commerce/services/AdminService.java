package com.example.E_Commerce.services;

import com.example.E_Commerce.entity.User;
import com.example.E_Commerce.repository.ProductRepository;
import com.example.E_Commerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public AdminService(UserRepository userRepository, ProductRepository productRepository){
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public void deleteUser(Long userId){
        if(!userRepository.existsById(userId)){
            throw new RuntimeException("user not found with user Id " + userId);
        }

        userRepository.deleteById(userId);
    }

    public void removeProduct(Long productId){
        if(!productRepository.existsById(productId)){
            throw new RuntimeException("product not found with product Id " + productId);
        }

        productRepository.deleteById(productId);
    }


}
