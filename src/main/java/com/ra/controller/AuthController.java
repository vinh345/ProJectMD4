package com.ra.controller;


import com.ra.model.dto.request.FormLogin;
import com.ra.model.dto.request.FormRegister;
import com.ra.model.dto.response.JWTResponse;
import com.ra.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api.myservice.com/v1/auth")
public class AuthController {

    // login
    @Autowired
    private IUserService userService;
    @PostMapping("/sign-in")
    public ResponseEntity<JWTResponse> doLogin(@RequestBody FormLogin formLogin){
        return new ResponseEntity<>(userService.login(formLogin), HttpStatus.OK);
    }

    // register
    @PostMapping("/sign-up")
    public ResponseEntity<?> doRegister(@RequestBody @Valid FormRegister formRegister){
        boolean check = userService.register(formRegister);
        if (check){
            Map<String,String> map = new HashMap<>();
            map.put("message","Account create successfully");
            return new ResponseEntity<>(map,HttpStatus.CREATED);
        }else {
            throw new RuntimeException("something is error");
        }
    }
}
