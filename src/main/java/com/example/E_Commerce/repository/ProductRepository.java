package com.example.E_Commerce.repository;

import com.example.E_Commerce.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {

    List<Products> findBySellerId(Long sellerId);
}
