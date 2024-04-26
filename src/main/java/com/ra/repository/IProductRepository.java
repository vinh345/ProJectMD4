package com.ra.repository;

import com.ra.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductNameContainingOrDescriptionContaining(String productName, String description);
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);


}
