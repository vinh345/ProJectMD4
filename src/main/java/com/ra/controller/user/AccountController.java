package com.ra.controller.user;

import com.ra.exception.DataNotFoundException;
import com.ra.model.dto.request.FormUserEdit;
import com.ra.model.dto.request.FormUserPasswordChange;
import com.ra.model.dto.response.UserResponse;
import com.ra.security.principle.UserDetailsCustom;
import com.ra.service.userService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api.myservice.com/v1/user/account")
public class AccountController {

    @Autowired
    private IUserService userService;


    /**
     * Thông tin tài khoản người dùng
     * @param authentication
     * @return
     * @throws DataNotFoundException
     */
    @GetMapping
    public ResponseEntity<UserResponse> getUser(Authentication authentication) throws DataNotFoundException {
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
        ResponseEntity<UserResponse> user = userService.findByUserName(userDetailsCustom.getUsername());
        return user;
    }
    
    /**
     * Cập nhật thông tin người dùng
     * @param formUserEdit
     * @param authentication
     * @return
     * @throws DataNotFoundException
     */
    @PutMapping
    public ResponseEntity<FormUserEdit> editUser(@RequestBody FormUserEdit formUserEdit, Authentication authentication) throws DataNotFoundException {
    	UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
    	return userService.editUser(userDetailsCustom.getUsername(), formUserEdit);
    }
    
    /**
     * Thay đổi mật khẩu (payload : oldPass, newPass, confirmNewPass)
     * @param authentication
     * @param form
     * @return
     * @throws DataNotFoundException
     */
    @PutMapping("change-password")
    public ResponseEntity<Void> changePassword(Authentication authentication,@RequestBody FormUserPasswordChange form) throws DataNotFoundException {
    	UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
    	return userService.changePassword(userDetailsCustom.getUsername(), form);
    }
}
