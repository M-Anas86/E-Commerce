package com.example.E_Commerce.services;

import com.example.E_Commerce.dto.OrderItemRequest;
import com.example.E_Commerce.entity.Order;
import com.example.E_Commerce.entity.OrderItem;
import com.example.E_Commerce.entity.Products;
import com.example.E_Commerce.entity.User;
import com.example.E_Commerce.repository.OrderRepository;
import com.example.E_Commerce.repository.ProductRepository;
import com.example.E_Commerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository){
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Order checkout(Long buyerId, List<OrderItemRequest> itemRequests) {

        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        Order order = new Order();
        order.setBuyer(buyer);
        order.setOrderDate(LocalDateTime.now());

        double totalAmount = 0.0;
        Integer quantity = 0;
        Integer price = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest request : itemRequests) {
            Products product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with ID : " + request.getProductId()));

            if (product.getStock() < request.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName() + " Available: " + product.getStock());
            }

            product.setStock(product.getStock() - request.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(request.getQuantity());
            orderItem.setPriceAtPurchase(product.getPrice());

            totalAmount += product.getPrice() * request.getQuantity();
            orderItems.add(orderItem);

            quantity = request.getQuantity();
            price = product.getPrice();
        }

        order.setTotalAmount(totalAmount);
        order.setOrderItems(orderItems);
        order.setQuantity(quantity);
        order.setPrice(price);

        return orderRepository.save(order);
    }

    public List<Order> getBuyerOrderHistory(Long buyerId){

        return orderRepository.findByBuyerId(buyerId);
    }
}
