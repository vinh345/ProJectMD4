package com.ra.repository;

import com.ra.model.entity.OrderDetail;
import com.ra.model.entity.OrderDetailId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
}
