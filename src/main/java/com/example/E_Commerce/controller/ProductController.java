package com.example.E_Commerce.controller;

import com.example.E_Commerce.dto.ProductRequest;
import com.example.E_Commerce.entity.Products;
import com.example.E_Commerce.entity.User;
import com.example.E_Commerce.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/add")
    public Products CreateProduct(@Valid @RequestBody ProductRequest productRequest){


        Products product = new Products();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());


        User sellerStub = new User();
        sellerStub.setId(productRequest.getSellerId());
        product.setSeller(sellerStub);

        return productService.addProduct(product);
    }


    @GetMapping
    public List<Products> getAllProducts(){

        return productService.getAllProducts();
    }
}
