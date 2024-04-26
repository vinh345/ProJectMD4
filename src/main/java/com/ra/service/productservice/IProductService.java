package com.ra.service.productservice;

import com.ra.exception.DataNotFoundException;
import com.ra.exception.NoDataException;
import com.ra.model.entity.Product;
import com.ra.service.IGenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

    public interface IProductService extends IGenericService<Product,Integer> {
        List<Product> search(String name) throws NoDataException;
        Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
        Product findById(Long id) throws DataNotFoundException;
        List<Product> getNewProducts();
    }
