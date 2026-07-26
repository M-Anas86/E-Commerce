package com.example.E_Commerce.services;


import com.example.E_Commerce.entity.Products;
import com.example.E_Commerce.entity.Role;
import com.example.E_Commerce.entity.User;
import com.example.E_Commerce.repository.ProductRepository;
import com.example.E_Commerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }


    public Products addProduct(Products product) {

        if (product.getSeller() == null || product.getSeller().getId() == null) {
            throw new RuntimeException("Validation error: product must linked to a valid seller id");
        }

        Long sellerId = product.getSeller().getId();

        User seller = userRepository.findById(sellerId).orElseThrow(() -> new RuntimeException("User not found with ID:" + sellerId));

        if (seller.getRole() != Role.SELLER) {
            throw new RuntimeException("Access denied: Only registered seller can upload products");
        }

        product.setSeller(seller);
        return productRepository.save(product);
    }


    public List<Products> getAllProducts() {

        return productRepository.findAll();
    }
}
