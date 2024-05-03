package com.ra.controller.admin;

import com.ra.exception.DataNotFoundException;
import com.ra.model.dto.response.PageResponse;
import com.ra.model.entity.User;
import com.ra.service.userService.IUserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/admin/users")
public class UserController {

    @Autowired private IUserService userService;

    /**
     * Lấy ra danh sách người dùng  (phân trang và sắp xếp)
     * @param pageable
     * @return
     */
    @GetMapping
    public ResponseEntity<PageResponse> findAllUser(@PageableDefault Pageable pageable) {
        return userService.findAllUser(pageable);
    }

    /**
     * Khóa / Mở khóa người dùng
     * @param userId
     * @return
     * @throws DataNotFoundException
     */
    @PutMapping("{userId}")
    public ResponseEntity<String> changeUserStatus(@PathVariable("userId") String userId) throws DataNotFoundException {
        return userService.changeUserStatus(userId);
    }

    /**
     * Tìm kiếm người dùng theo tên
     * @param name
     * @return
     */
    @GetMapping("search")
    public ResponseEntity<List<User>> findByName(@RequestParam("name") String name) {
        return userService.findByUsernameContaining(name);
    }
}
