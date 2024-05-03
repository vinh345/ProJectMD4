package com.ra.controller.admin;

import com.ra.exception.DataNotFoundException;
import com.ra.exception.IdFormatException;
import com.ra.model.entity.Orders;
import com.ra.service.orderService.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api.myservice.com/v1/admin/orders")
public class OrderController {

    @Autowired private IOrderService orderService;

    /**
     * Danh sách tất cả đơn hàng
     * @param pageable
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<Orders>> findAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findAll(pageable));
    }


    /**
     * Danh sách đơn hàng theo trạng thái
     * @param status
     * @return
     */
    @GetMapping("/{status}/status")
    public ResponseEntity<Page<Orders>> findByStatus(@PathVariable String status,Pageable pageable) {
        return new  ResponseEntity<>(orderService.findByStatus(status,pageable),HttpStatus.OK);
    }

    /**
     * Chi tiết đơn hàng
     * @param orderId
     * @return
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> findById(@PathVariable("orderId")  String orderId ) throws DataNotFoundException, IdFormatException {
        return orderService.findById(orderId);
    }


    /**
     * Cập nhật trạng thái đơn hàng (payload : orderStatus)
     * @param orderId
     * @param orderStatus
     * @return
     * @throws IdFormatException
     * @throws DataNotFoundException
     */
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Orders> changeStatus(
            @PathVariable String orderId,
            @RequestParam String orderStatus) throws IdFormatException, DataNotFoundException {
        return orderService.changeStatus(orderId, orderStatus);
    }
}
