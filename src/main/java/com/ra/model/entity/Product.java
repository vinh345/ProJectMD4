package com.ra.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
	@Id @GeneratedValue private Long productId;
    
    @Column(columnDefinition = "Varchar(100)") private String sku;
    
    @Column(columnDefinition = "Varchar(100)") private String productName;
    
    @Column(columnDefinition = "Text") private String description;
    
    @Column(columnDefinition = "Decimal(10,2)") private BigDecimal price;
    
    private Integer stockQuantity;
    
    private String image;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties({"id", "description", "status"})
    private Category category;

    private Date createdAt;
    
    private Date updatedAt;
}