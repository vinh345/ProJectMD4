package com.ra.service.IAdminService.RoleService;

import com.ra.exception.NoDataException;
import com.ra.model.entity.Role;
import com.ra.repository.IProductRepository;
import com.ra.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IRoleServiceIMPL implements IRoleService{

    @Autowired
    IRoleRepository roleRepository;
    @Override
    public Page<Role> findAll(Pageable pageable) throws NoDataException {
        return null;
    }

    @Override
    public Optional<Role> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Role save(Role role) {
        return null;
    }

    @Override
    public void remove(Long aLong) {

    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
