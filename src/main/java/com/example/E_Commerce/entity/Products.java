package com.example.E_Commerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product can not be empty")
    @Size(min = 2, max = 100, message = "Product name should be bewteen 2 to 100")
    private String name;

    @NotBlank(message = "Description can not be empty")
    private String description;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price can not be negative")
    private Integer price;

    @NotNull(message = "Stock level is required")
    @Min(value = 0, message = "Stock level can not be negative")
    private Integer stock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id", nullable = false)
    @JsonIgnoreProperties({"password", "email", "username", "role"})
    private User seller;
}
