package com.ra.model.dto.request;

import java.math.BigDecimal;

import com.ra.model.entity.Product;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String sku;
    private String productName;
    private String description;
    private BigDecimal price;

    // Constructor to copy data from Product to ProductDto
    public ProductDto(Product product) {
        this.id = product.getProductId();
        this.sku = product.getSku();
        this.productName = product.getProductName();
        this.description = product.getDescription();
        this.price = product.getPrice();
    }

    // Getters and setters
}
