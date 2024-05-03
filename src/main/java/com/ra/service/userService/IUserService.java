package com.ra.service.userService;


import java.util.List;

import com.ra.model.dto.response.UserResponse;
import org.apache.http.auth.AuthenticationException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.ra.exception.DataConflictException;
import com.ra.exception.DataNotFoundException;
import com.ra.model.dto.request.FormLogin;
import com.ra.model.dto.request.FormRegister;
import com.ra.model.dto.request.FormUserEdit;
import com.ra.model.dto.request.FormUserPasswordChange;
import com.ra.model.dto.response.JWTResponse;
import com.ra.model.dto.response.PageResponse;
import com.ra.model.entity.User;
import com.ra.service.IGenericService;

public interface IUserService extends IGenericService<User,Long> {

    ResponseEntity<User> register(FormRegister formRegister) throws DataNotFoundException, DataConflictException;

    ResponseEntity<JWTResponse> login(FormLogin formLogin) throws AuthenticationException;

    ResponseEntity<PageResponse> findAllUser(Pageable pageable);

    List<User> findAllExceptAdmin();

    ResponseEntity<UserResponse> findByUserName(String name) throws DataNotFoundException;
    
    ResponseEntity<FormUserEdit> editUser(String name, FormUserEdit formUserEdit) throws DataNotFoundException;
    
    ResponseEntity<Void> changePassword(String name, FormUserPasswordChange form) throws DataNotFoundException;

    ResponseEntity<List<User>> findByUsernameContaining(String name);

    ResponseEntity<String> changeUserStatus(String userId) throws DataNotFoundException;
}
