package com.ra.service.orderService;

import com.ra.Common;
import com.ra.exception.DataNotFoundException;
import com.ra.exception.IdFormatException;
import com.ra.model.dto.response.OrderResponse;
import com.ra.model.entity.Orders;
import com.ra.model.entity.OrderStatus;
import com.ra.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceIMPL implements IOrderService {

    @Autowired private IOrderRepository orderRepository;

    @Override
    public Page<Orders> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Optional<Orders> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Orders save(Orders order) {
        return orderRepository.save(order);
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }


    @Override
    public ResponseEntity<Page<Orders>> findByStatusAndUserId(String status, Pageable pageable, Long userId) {
        return new ResponseEntity<>(orderRepository.findAllByStatusAndUserUserId(OrderStatus.valueOf(status),userId,pageable),HttpStatus.OK);
    }

    @Override
    public Page<Orders> findByStatus(String orderStatus, Pageable pageable) {
        return orderRepository.findByStatus(OrderStatus.valueOf(orderStatus), pageable);
    }


    @Override
    public Page<Orders> getOrderByUserName(Long userId, Pageable pageable) {
        return orderRepository.findAllByUserUserId(userId,pageable);
    }


    @Override
    public ResponseEntity<Orders> findById(String orderId) throws DataNotFoundException, IdFormatException {
        Long id = Common.getLong(orderId, "orderId không phải là số");
        Optional<Orders> orderDetails = findById(id);
        if (orderDetails.isEmpty()) {
            throw new DataNotFoundException("orderId không tìm thấy");
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderDetails.get());
    }

    @Override
    public ResponseEntity<Orders> changeStatus(String orderId, String orderStatus) throws IdFormatException, DataNotFoundException {
        Long id = Common.getLong(orderId, "orderId không phải là số");
        OrderStatus status = Common.getOrderStatus(orderStatus, "orderStatus không hợp lệ");
        Optional<Orders> orders = findById(id);
        if (orders.isEmpty()) {
            throw new DataNotFoundException("orderId không tìm thấy");
        }
        orders.get().setStatus(status);
        save(orders.get());
        return ResponseEntity.status(HttpStatus.OK).body(orders.get());
    }


    @Override
    public Optional<Orders> getOrderBySerialNumber(String serialNumber) {
        return orderRepository.findBySerialNumber(serialNumber);
    }
}
