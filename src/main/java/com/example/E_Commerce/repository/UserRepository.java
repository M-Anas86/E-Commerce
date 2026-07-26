package com.example.E_Commerce.repository;

import com.example.E_Commerce.entity.Products;
import com.example.E_Commerce.entity.Role;
import com.example.E_Commerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    List<User> findByRole(String role);

    List<Products> findByIdAndRole(Long buyerId, String Role);


}
