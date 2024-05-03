package com.ra.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingCartResponse {
    private Integer shoppingCartId;
    private String sku;
    private String productName;
    private String description;
    private BigDecimal price;
    private String image;
    private String categoryName;
    private String username;
}
