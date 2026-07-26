package com.example.E_Commerce.repository;

import com.example.E_Commerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByBuyerId(Long BuyerId);

    List<Order> findByBuyerIdAndStatus(Long buyerId, String status);
}
