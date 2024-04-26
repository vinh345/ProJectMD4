package com.ra.service.productservice;

import com.ra.exception.DataNotFoundException;
import com.ra.exception.NoDataException;
import com.ra.model.entity.Product;
import com.ra.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IProductServiceIMPL implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Override
    public Page<Product> findAll(Pageable pageable) throws NoDataException {
        Page<Product> list = productRepository.findAll(pageable);
        if (list.isEmpty()) {
            throw new NoDataException("danh sách trống");
        }
        return list;
    }

    @Override
    public Optional<Product> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public void remove(Integer integer) {

    }

    public List<Product> search(String name) throws NoDataException {
        List<Product> list = productRepository.findByProductNameContainingOrDescriptionContaining(name, name);
        if(list.isEmpty()) {
            throw new NoDataException(" danh sách trống");
        }
        return list;
    }

    @Override
    public Page<Product> findByCategoryId(Long categoryId, Pageable pageable) {

        Page<Product> page = productRepository.findByCategoryId(categoryId, pageable);
        return page;
    }

    @Override
    public Product findById(Long id) throws DataNotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new DataNotFoundException("id not found"));
    }

    public List<Product> getNewProducts() {
        // Sử dụng phương thức findAll trong repository và sắp xếp theo createdAt để lấy ra các sản phẩm mới nhất
        Pageable pageable = PageRequest.of(0, 5, Sort.by("createdAt").descending());
        List<Product> content = productRepository.findAll(pageable).getContent();
        return content;
    }
}
