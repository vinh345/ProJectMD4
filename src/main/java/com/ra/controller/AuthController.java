package com.ra.controller;


import org.apache.http.auth.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ra.exception.DataConflictException;
import com.ra.exception.DataNotFoundException;
import com.ra.model.dto.request.FormLogin;
import com.ra.model.dto.request.FormRegister;
import com.ra.model.dto.response.JWTResponse;
import com.ra.model.entity.User;
import com.ra.service.userService.IUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api.myservice.com/v1/auth")
public class AuthController {

    @Autowired private IUserService userService;

    /**
     * Đăng kí tài khoản người dùng
     * @param formRegister
     * @return
     */
    @PostMapping("/sign-up")
    public ResponseEntity<User> register(@RequestBody @Valid FormRegister formRegister) throws DataNotFoundException, DataConflictException {
        return userService.register(formRegister);
    }

    /**
     * Đăng nhập tài khoản bằng username và password
     * @param formLogin
     * @return
     */
    @PostMapping("/sign-in")
    public ResponseEntity<JWTResponse> login(@RequestBody FormLogin formLogin) throws AuthenticationException {
        return userService.login(formLogin);
    }
}
