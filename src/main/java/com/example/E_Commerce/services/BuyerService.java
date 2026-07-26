package com.example.E_Commerce.services;

import com.example.E_Commerce.dto.ProductDto;
import com.example.E_Commerce.dto.UserDto;
import com.example.E_Commerce.entity.Order;
import com.example.E_Commerce.entity.OrderItem;
import com.example.E_Commerce.entity.Products;
import com.example.E_Commerce.entity.User;
import com.example.E_Commerce.repository.OrderRepository;
import com.example.E_Commerce.repository.ProductRepository;
import com.example.E_Commerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuyerService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final OrderRepository orderRepository;


    public BuyerService(UserRepository userRepository, OrderRepository orderRepository){
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }


    public List<UserDto> getAllBuyers(){
        List<User> buyers = userRepository.findByRole("BUYER");

        return buyers.stream().map(buyer ->{
            UserDto dto = new UserDto();
            dto.setId(buyer.getId());
            dto.setName(buyer.getName());
            dto.setEmail(buyer.getEmail());
            return dto;
        }).collect(Collectors.toList());
    }

    public User getBuyerDetails(Long id){
        return userRepository.findById(id).
                orElseThrow(() -> new RuntimeException("User not found with product id " + id));
    }

    private ProductDto toProductDto(Products product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        return dto;
    }

    public UserDto getBuyerWithPurchasedProducts(Long buyerId){
        User buyer = userRepository.findById(buyerId)
              .orElseThrow(() -> new RuntimeException("Buyer not found with id:" + buyerId));

        List<Order> orders = orderRepository.findByBuyerIdAndStatus(buyerId, "BUYER");

        //List<SpringDataJaxb.OrderDto> orderHistory = orderService.getOrdersByBuyerId(buyerId);

        List<ProductDto> boughtProducts = orders.stream()
                .flatMap(order -> order.getOrderItems().stream())
                .map(OrderItem::getProduct)
                .distinct()
                .map(this::toProductDto)
                .collect(Collectors.toUnmodifiableList());

        UserDto userDto = new UserDto();
        userDto.setId(buyer.getId());
        userDto.setName(buyer.getName());
        userDto.setEmail(buyer.getEmail());
        userDto.setPurchasedProducts(boughtProducts);

        return userDto;

    }
}
