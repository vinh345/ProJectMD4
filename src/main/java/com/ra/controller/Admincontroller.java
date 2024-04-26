package com.ra.controller;

import com.ra.exception.DataNotFoundException;
import com.ra.model.entity.Role;
import com.ra.model.entity.User;
import com.ra.service.IAdminService.RoleService.IRoleService;
import com.ra.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api.myservice.com/v1/admin")
public class Admincontroller {
    @Autowired
    IRoleService roleService;

    @Autowired
    IUserService userService;

    // hiển thị ng dùng trừ admin
    @GetMapping("/users")
    public ResponseEntity<List<User>> findAllUsersExceptAdmin() {
        List<User> users = userService.findAllExceptAdmin();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }


    // hiển thị danh sách quyền
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.findAll();
        if (roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(roles);
    }

    // tìm kiếm người dùng theo tên
    @GetMapping("users/search")
    public ResponseEntity<List<User>> findByUserName(@RequestParam String name) throws DataNotFoundException {
        List<User> findByUserName = userService.findByUserName(name);
        return new ResponseEntity<>(findByUserName, HttpStatus.OK);
    }

    // khóa/mở khóa người dùng
    @PutMapping("users/{userId}")
    public ResponseEntity<String> toggleUserStatus(@PathVariable Long userId) throws DataNotFoundException {
        String massage =  userService.toggleUserStatus(userId);
        return new ResponseEntity<>(massage,HttpStatus.OK);
    }
}
