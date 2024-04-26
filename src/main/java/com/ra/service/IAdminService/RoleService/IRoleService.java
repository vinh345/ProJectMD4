package com.ra.service.IAdminService.RoleService;

import com.ra.model.entity.Role;
import com.ra.service.IGenericService;

import java.util.List;

public interface IRoleService extends IGenericService<Role,Long> {
    List<Role> findAll();
}
