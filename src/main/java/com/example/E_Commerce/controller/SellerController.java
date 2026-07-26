package com.example.E_Commerce.controller;

import com.example.E_Commerce.dto.ProductRequest;
import com.example.E_Commerce.entity.Products;
import com.example.E_Commerce.entity.User;
import com.example.E_Commerce.services.ProductService;
import com.example.E_Commerce.services.SellerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seller")
public class SellerController {

    private final SellerService sellerService;
    private final ProductService productService;

    @Autowired
    public SellerController(SellerService sellerService, ProductService productService){
        this.sellerService = sellerService;
        this.productService = productService;
    }

    @PostMapping("/id/product/add")
    public Products addProduct(@Valid @RequestBody ProductRequest productRequest){
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

    @GetMapping("/{sellerId}/products")
    public List<Products> getMyDashboard(@PathVariable Long sellerId){
        return sellerService.getProductBySeller(sellerId);
    }

    @PatchMapping("/{sellerId}/products/{productId}/stock")
    public Products updateStock(
            @PathVariable Long sellerId,
            @PathVariable Long productId,
            @RequestParam Integer stock){
        return sellerService.updateProductStock(productId, stock, sellerId);
    }

    @DeleteMapping("/products/{productId}/seller/{sellerId}")
    public String moderateProduct(@PathVariable Long productId, Long sellerId){
        sellerService.removeProduct(productId, sellerId);
        return "Product listed with id " + productId + " has successfully moderated";
    }
}
