package com.ra.repository;

import com.ra.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    
	List<Product> findByProductNameContainingOrDescriptionContaining(String productName, String description);

    @Query("from Product product order by product.createdAt DESC limit 5")
    List<Product> getNewProducts();
    
    Page<Product> findByCategoryCategoryId(Long categoryId, Pageable pageable);


}
