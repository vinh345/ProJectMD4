package com.ra.repository;


import com.ra.model.entity.RoleName;
import com.ra.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    List<User> findByUsername(String username);

    List<User> findByRoleSetRoleNameNot(RoleName roleName);

}
