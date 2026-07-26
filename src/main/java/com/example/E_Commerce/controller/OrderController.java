package com.example.E_Commerce.controller;


import com.example.E_Commerce.dto.OrderItemRequest;
import com.example.E_Commerce.entity.Order;
import com.example.E_Commerce.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping("/checkot/{buyerId}")
    public Order placeOrder(@PathVariable Long buyerId, @RequestBody List<OrderItemRequest> items){
        return orderService.checkout(buyerId, items);
    }

    @GetMapping("/history/{buyerId}")
    public List<Order> getHistory(@PathVariable Long buyerId){
        return orderService.getBuyerOrderHistory(buyerId);
    }
}
