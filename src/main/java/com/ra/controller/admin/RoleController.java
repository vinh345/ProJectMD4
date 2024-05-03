package com.ra.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ra.model.entity.Role;
import com.ra.service.roleService.IRoleService;

@RestController
@RequestMapping("/api.myservice.com/v1/admin/roles")
public class RoleController {

    @Autowired private IRoleService roleService;

    /**
     * 
     * Lấy về danh sách quyền 
     * @param pageable
     * @return
     */
    @GetMapping
    public Page<Role> findAll(@PageableDefault Pageable pageable) {
        return roleService.findAll(pageable);
    }
}
