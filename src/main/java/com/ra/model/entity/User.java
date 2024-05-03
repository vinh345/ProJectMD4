package com.ra.model.entity;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
	@Id @GeneratedValue private Long userId;
    
    @Column(columnDefinition = "Varchar(100)")
    private String username;
    
    @Email private String email;
    
    @Column(columnDefinition = "Varchar(100)") private String fullName;
    
    @Column(columnDefinition = "Bit") private Boolean status;
    
    private String password;
    
    private String avatar;
    
    @Column(columnDefinition = "Varchar(15)")  private String phone;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roleSet;

    private Date createdAt;
    
    private Date updatedAt;
    
    @Column(columnDefinition = "Bit") private  Boolean isDeleted;
}
