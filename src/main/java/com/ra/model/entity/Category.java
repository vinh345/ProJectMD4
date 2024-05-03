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
public class Category {
    
	@Id @GeneratedValue private Long categoryId;
    
	@Column(columnDefinition = "Varchar(100)", nullable = false) private String categoryName;
    
	@Column(columnDefinition = "Text") private String description;
	
	@Column(columnDefinition = "Bit") private Boolean status;
}

