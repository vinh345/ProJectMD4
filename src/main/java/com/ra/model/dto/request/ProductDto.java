package com.ra.model.dto.request;

import com.ra.model.entity.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

public class ProductDto {
    private Long id;
    private String sku;
    private String productName;
    private String description;
    private BigDecimal price;

    // Constructor to copy data from Product to ProductDto
    public ProductDto(Product product) {
        this.id = product.getId();
        this.sku = product.getSku();
        this.productName = product.getProductName();
        this.description = product.getDescription();
        this.price = product.getPrice();
    }

    // Getters and setters
}
