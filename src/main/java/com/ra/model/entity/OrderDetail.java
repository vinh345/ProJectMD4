package com.ra.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@IdClass(OrderDetailId.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {

	@Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Orders order;

	@Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Product product;
    
	@Column(columnDefinition = "Varchar(100)") private String name;
	
    @Column(columnDefinition = "Decimal(10,2)") private BigDecimal unitPrice;
    
    private Integer orderQuantity;
}
