package com.ra.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fullAddress;
    private Integer phone;
    private String receiveName;

    @ManyToOne
    @JoinColumn(name = "user_id") // tên cột trong csdl được ánh xạ đến
    private User user;
}
