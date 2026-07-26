package com.example.E_Commerce.services;

import com.example.E_Commerce.entity.Products;
import com.example.E_Commerce.entity.Role;
import com.example.E_Commerce.entity.User;
import com.example.E_Commerce.repository.ProductRepository;
import com.example.E_Commerce.repository.UserRepository;
import org.hibernate.dialect.unique.CreateTableUniqueDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.admin.SpringApplicationAdminMXBeanRegistrar;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public SellerService(ProductRepository productRepository, UserRepository userRepository){
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Products addProduct(Products product){
        if(product.getSeller() == null || product.getSeller().getId() == null){
            throw new RuntimeException("Validation error: Product must link with a valid seller. ");
        }

        Long sellerId = product.getSeller().getId();

        User seller = userRepository.findById(sellerId).orElseThrow(() -> new RuntimeException("User not found wit id: " + sellerId));

        product.setSeller(seller);
        return productRepository.save(product);
    }

    public List<Products> getProductBySeller(Long sellerId){

        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found with Id " + sellerId));

        if(seller.getRole() != Role.SELLER){
            throw new RuntimeException("Access denied: user not registered as seller");
        }

        return productRepository.findBySellerId(sellerId);
    }


    public Products updateProductStock(Long productId, Integer newStock, Long sellerId){
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with Id" + productId));

        if(!product.getSeller().getId().equals(sellerId)){
            throw  new RuntimeException("Access denied : you do not own this product");
        }

        product.setStock(newStock);
        return productRepository.save(product);
    }

    public void removeProduct(Long productId, Long sellerId){
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with Id" + productId));

            if(!product.getSeller().getId().equals(sellerId)){
                throw  new RuntimeException("Access denied : you do not own this product");
            }

        productRepository.deleteById(productId);
    }
}
