package com.ra.model.entity;

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
public class Orders {

    @Id
    @GeneratedValue
    private Long orderId;

    @Column(columnDefinition = "Varchar(100)")
    private String serialNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private User user;

    @Column(columnDefinition = "Decimal(10,2)")
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(columnDefinition = "Varchar(100)")
    private String note;

    @Column(columnDefinition = "Varchar(255)")
    private String receiveAddress;

    @Column(columnDefinition = "Varchar(100)")
    private String receiveName;

    @Column(columnDefinition = "Varchar(15)")
    private Integer receivePhone;

    private Date createdAt;

    private Date receivedAt;

}
