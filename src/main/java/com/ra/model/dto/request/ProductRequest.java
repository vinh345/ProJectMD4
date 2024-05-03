package com.ra.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductRequest {
    private String productName;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String url;
    private Long categoryId;
}
