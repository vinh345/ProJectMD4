package com.ra.service.productService;

import java.util.List;

import com.ra.model.dto.request.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;

import com.ra.exception.DataNotFoundException;
import com.ra.exception.IdFormatException;
import com.ra.model.dto.response.PageResponse;
import com.ra.model.entity.Product;
import com.ra.service.IGenericService;

public interface IProductService extends IGenericService<Product,Long> {

    ResponseEntity<PageResponse> findAllProducts(@PageableDefault Pageable pageable);

    ResponseEntity<List<Product>> findByNameOrDescription(String name);

    ResponseEntity<Page<Product>> findByCategoryId(Long categoryId, Pageable pageable);

    ResponseEntity<List<Product>> getNewProducts();
    
    ResponseEntity<Product> findById(String productId) throws DataNotFoundException;

    ResponseEntity<Product> save(ProductRequest request) throws DataNotFoundException;

    ResponseEntity<Product> update(String productId, ProductRequest request) throws DataNotFoundException, IdFormatException;

    ResponseEntity<Void> delete(String productId) throws IdFormatException;

    String generateSku(String productName);
}
