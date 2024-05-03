package com.ra.controller.user;

import com.ra.model.entity.Orders;
import com.ra.security.principle.UserDetailsCustom;
import com.ra.service.orderService.IOrderService;
import com.ra.service.orderService.OrderServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api.myservice.com/v1/user/history")
public class HistoryController {

    @Autowired
    private IOrderService orderService;

    /**
     * lấy ra danh sách lịch sử mua hàng
     *
     * @param userDetails
     * @param pageable
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<Orders>> history(@AuthenticationPrincipal UserDetailsCustom userDetails, @PageableDefault Pageable pageable) {
        Page<Orders> orderHistory = orderService.getOrderByUserName(userDetails.getId(), pageable);
        return new ResponseEntity<>(orderHistory, HttpStatus.OK);
    }


    /**
     * lấy ra  chi tiết đơn hàng theo số seria
     *
     * @param serialNumber
     * @return
     */
    @GetMapping("/{serialNumber}")
    public ResponseEntity<Optional<Orders>> orderDetailBySerial(@PathVariable String serialNumber) {
        Optional<Orders> orderDetail = orderService.getOrderBySerialNumber(serialNumber);
        return new ResponseEntity<>(orderDetail, HttpStatus.OK);
    }


    /**
     * lấy ra danh sách lịch sử đơn hàng theo trạng thái đơn
     *
     * @param orderStatus
     * @param pageable
     * @return
     */
    @GetMapping("/{orderStatus}/status")
    public ResponseEntity<Page<Orders>> orderByStatus(@AuthenticationPrincipal UserDetailsCustom userDetails, @PathVariable String orderStatus, Pageable pageable) {
        ResponseEntity<Page<Orders>> orders = orderService.findByStatusAndUserId(orderStatus, pageable, userDetails.getId());
        return orders;
    }
}
