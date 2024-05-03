package com.ra.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    
	@Id @GeneratedValue private Long addressId;

    @ManyToOne
    @JoinColumn
    private User user;
    
	private String fullAddress;
    
	@Column(columnDefinition = "varchar(15)") private String phone;
    
	@Column(columnDefinition = "varchar(50)") private String receiveName;
}
