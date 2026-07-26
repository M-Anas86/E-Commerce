package com.example.E_Commerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductRequest {

    @NotBlank(message = "Product name can't be empty")
    @Size(min = 2, max = 100, message = "Product name should be between 2 to 100")
    private String name;

    @NotBlank(message = "Description can't be blank")
    private String description;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price can't be negative")
    private Integer price;


    @NotNull(message = "Stock level is required")
    @Min(value = 0, message = "Stock level can't be negative")
    private Integer stock;

    @NotNull(message = "sellerId is required")
    private Long sellerId;
}
