package com.example.E_Commerce.controller;
import com.example.E_Commerce.dto.UserDto;
import com.example.E_Commerce.entity.Products;
import com.example.E_Commerce.entity.User;
import com.example.E_Commerce.services.BuyerService;
import com.example.E_Commerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/buyer")
public class BuyerController {

    private final BuyerService buyerService;
    private final UserService userService;

    @Autowired
    public BuyerController(BuyerService buyerService, UserService userService){

        this.buyerService = buyerService;
        this.userService = userService;
    }

    @GetMapping()
    public List<UserDto> browseUser(){
        return buyerService.getAllBuyers();
    }

    @GetMapping("/{id}")
    public User viewProduct(@PathVariable Long id){

        return buyerService.getBuyerDetails(id);
    }

//    @GetMapping("/details/{buyerId}")
//    public ResponseEntity<UserDto> getBuyerDetailWithProducts(@PathVariable Long buyerId){
//        UserDto buyerProfile = userService.getBuyersWithPurchasedProducts(buyerId);
//        return ResponseEntity.ok(buyerProfile);
//    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<UserDto> getBuyerProfile(@PathVariable Long id){
        UserDto buyerProfile = buyerService.getBuyerWithPurchasedProducts(id);
        return ResponseEntity.ok(buyerProfile);
    }
}
