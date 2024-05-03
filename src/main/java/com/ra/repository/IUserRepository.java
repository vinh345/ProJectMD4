package com.ra.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ra.model.entity.RoleName;
import com.ra.model.entity.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findByUsernameContaining(String username);

    List<User> findByRoleSetRoleNameNot(RoleName roleName);

    

}
