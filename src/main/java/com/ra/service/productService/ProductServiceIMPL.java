package com.ra.service.productService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ra.Common;
import com.ra.model.dto.request.ProductRequest;
import com.ra.model.entity.Category;
import com.ra.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ra.exception.DataNotFoundException;
import com.ra.exception.IdFormatException;
import com.ra.model.dto.response.PageResponse;
import com.ra.model.entity.Product;
import com.ra.repository.IProductRepository;

@Service
public class ProductServiceIMPL implements IProductService {

    @Autowired private IProductRepository productRepository;
    @Autowired private ICategoryRepository categoryRepository;

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ResponseEntity<PageResponse> findAllProducts(Pageable pageable) {
        Page<Product> productPage= productRepository.findAll(pageable);
        PageResponse response = PageResponse.builder()
                .data(productPage.getContent())
                .totalPages(productPage.getTotalPages())
                .last(productPage.isLast())
                .first(productPage.isFirst())
                .number(productPage.getNumber())
                .size(productPage.getSize())
                .sort(productPage.getSort())
                .totalElements(productPage.getNumberOfElements())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<List<Product>> findByNameOrDescription(String name) {
        List<Product> products = productRepository.findByProductNameContainingOrDescriptionContaining(name, name);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @Override
    public ResponseEntity<Page<Product>> findByCategoryId(Long categoryId, Pageable pageable) {
        Page<Product> products = productRepository.findByCategoryCategoryId(categoryId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @Override
    public ResponseEntity<List<Product>> getNewProducts() {
        List<Product> newProducts = productRepository.getNewProducts();
        return ResponseEntity.status(HttpStatus.OK).body(newProducts);
    }

	@Override
	public ResponseEntity<Product> findById(String productId) throws DataNotFoundException {
		try {
			Long id = Long.valueOf(productId);
			Optional<Product> product = findById(id);
			if (product.isEmpty()) {
				throw new DataNotFoundException("productId không tìm thấy");
			}
			return ResponseEntity.status(HttpStatus.OK).body(product.get());
		} catch (NumberFormatException e) {
			throw new DataNotFoundException("productId không phải số");
		}
	}

    @Override
    public ResponseEntity<Product> save(ProductRequest request) throws DataNotFoundException {
        Optional<Category> category = categoryRepository.findById(request.getCategoryId());
        if (category.isEmpty()) {
            throw new DataNotFoundException("categoryId không tìm thấy");
        }
        Product product = new Product();
        product.setSku(generateSku(request.getProductName()));
        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setImage(request.getUrl());
        product.setCategory(category.get());
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());
        save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @Override
	public ResponseEntity<Product> update(String productId, ProductRequest request) throws DataNotFoundException, IdFormatException {
        Long id = Common.getLong(productId, "productId không phải số");
        Optional<Product> optional = findById(id);
        if (optional.isEmpty()) {
            throw new DataNotFoundException("productId không tìm thấy");
        }
        Optional<Category> category = categoryRepository.findById(request.getCategoryId());
        if (category.isEmpty()) {
            throw new DataNotFoundException("categoryId không tìm thấy");
        }
        optional.get().setProductName(request.getProductName());
        optional.get().setDescription(request.getDescription());
        optional.get().setPrice(request.getPrice());
        optional.get().setStockQuantity(request.getStockQuantity());
        optional.get().setImage(request.getUrl());
        optional.get().setUpdatedAt(new Date());
        productRepository.save(optional.get());
        return ResponseEntity.status(HttpStatus.OK).body(optional.get());
	}
	
	@Override
	public ResponseEntity<Void> delete(String productId) throws IdFormatException {
        Long id = Common.getLong(productId, "productId không phải số");
        productRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

    @Override
    public String generateSku(String productName) {
        // Triển khai logic tạo sku ở đây
        // Ví dụ:
        String normalizedProductName = productName.replaceAll("[^a-zA-Z0-9]", "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(new Date());
        return normalizedProductName + "_" + timestamp;
    }
}
