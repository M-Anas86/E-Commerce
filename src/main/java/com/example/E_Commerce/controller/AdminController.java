package com.example.E_Commerce.controller;

import com.example.E_Commerce.entity.User;
import com.example.E_Commerce.services.AdminService;
import com.example.E_Commerce.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final SellerService sellerService;

    @Autowired
    public AdminController(AdminService adminService, SellerService sellerService){

        this.adminService = adminService;
        this.sellerService = sellerService;
    }

    @GetMapping("/user")
    public List<User> viewAllUser(){
        return adminService.getAllUser();
    }

    @DeleteMapping("/user/{id}")
    public String banUser(@PathVariable Long id){
        adminService.deleteUser(id);
        return "User account with id " + id + " has been successfully removed.";
    }

    @DeleteMapping("/products/{productId}")
    public String moderateProduct(@PathVariable Long productId){
        adminService.removeProduct(productId);
        return "Product lsiting with id " + productId + " has been successfully moderated.";
    }
}
