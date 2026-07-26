package com.example.E_Commerce.dto;

import com.example.E_Commerce.entity.Products;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {
    private Long id;
    private String name;
    private String email;
    private List<ProductDto> purchasedProducts;
}
