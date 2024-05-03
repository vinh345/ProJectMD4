package com.ra.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ra.model.entity.Role;
import com.ra.model.entity.RoleName;

@Repository
public interface IRoleRepository extends JpaRepository<Role,Long> {
    
	Optional<Role> findByRoleName(RoleName roleName);
}
