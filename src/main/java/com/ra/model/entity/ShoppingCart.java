package com.ra.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart {
    
	@Id @GeneratedValue private Integer shoppingCartId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private User user;
    
	private Integer orderQuantity;
}
