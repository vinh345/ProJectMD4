package com.ra.service.orderService;

import com.ra.exception.DataNotFoundException;
import com.ra.exception.IdFormatException;
import com.ra.model.entity.Orders;
import com.ra.service.IGenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface IOrderService extends IGenericService<Orders, Long> {
    ResponseEntity<Page<Orders>> findByStatusAndUserId(String status, Pageable pageable,Long userId);

   Page<Orders> findByStatus(String orderStatus, Pageable pageable);

    Page<Orders> getOrderByUserName(Long userId, Pageable pageable);

    ResponseEntity<Orders> findById(String orderId) throws DataNotFoundException, IdFormatException;

    ResponseEntity<Orders> changeStatus(String orderId, String orderStatus) throws IdFormatException, DataNotFoundException;

    Optional<Orders> getOrderBySerialNumber(String serialNumber);
}
