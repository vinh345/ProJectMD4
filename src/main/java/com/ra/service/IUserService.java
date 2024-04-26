package com.ra.service;


import com.ra.exception.DataNotFoundException;
import com.ra.exception.NoDataException;
import com.ra.model.dto.request.FormLogin;
import com.ra.model.dto.request.FormRegister;
import com.ra.model.dto.response.JWTResponse;
import com.ra.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService extends IGenericService<User,Long>{
      boolean register(FormRegister formRegister);
        JWTResponse login(FormLogin formLogin);


   // Page<User> findAllExceptAdmin(Pageable pageable) throws NoDataException;

    List<User> findAllExceptAdmin();

    List<User> findByUserName(String name) throws DataNotFoundException;

    String toggleUserStatus(Long userId) throws DataNotFoundException;
}
