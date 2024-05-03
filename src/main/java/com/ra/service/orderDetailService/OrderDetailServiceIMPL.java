package com.ra.service.orderDetailService;

import com.ra.model.entity.OrderDetail;
import com.ra.model.entity.OrderDetailId;
import com.ra.repository.IOrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderDetailServiceIMPL implements IOrderDetailService {

    @Autowired private IOrderDetailRepository orderDetailRepository;

    @Override
    public Page<OrderDetail> findAll(Pageable pageable) {
        return orderDetailRepository.findAll(pageable);
    }

    @Override
    public Optional<OrderDetail> findById(OrderDetailId id) {
        return orderDetailRepository.findById(id);
 }


    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public void delete(OrderDetailId id) {
        orderDetailRepository.deleteById(id);
    }
}
