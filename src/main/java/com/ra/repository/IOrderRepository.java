package com.ra.repository;

import com.ra.model.entity.Orders;
import com.ra.model.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<Orders, Long> {

    Page<Orders> findByStatus(OrderStatus status,Pageable pageable);
    Page<Orders> findAllByStatusAndUserUserId(OrderStatus orderStatus,Long userId,Pageable pageable);

    @Query("select O from Orders O where O.user.username like :username")
    Page<Orders> getAllByUserName(@Param("username") String username, Pageable pageable);

    Page<Orders> findAllByUserUserId(Long userId, Pageable pageable);

    Optional<Orders> findBySerialNumber(String serialNumber);

}
