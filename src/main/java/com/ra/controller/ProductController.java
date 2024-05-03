package com.ra.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ra.exception.DataNotFoundException;
import com.ra.model.dto.response.PageResponse;
import com.ra.model.entity.Product;
import com.ra.service.productService.IProductService;

@RestController
@RequestMapping("/api.myservice.com/v1/products")
public class ProductController {

    @Autowired private IProductService productService;


    /**
     * Danh sách sản phẩm được bán(có phân trang và sắp xếp)
     * @param pageable
     * @return
     */
    @GetMapping
    public ResponseEntity<PageResponse> findAllProducts(@PageableDefault Pageable pageable) {
        return productService.findAllProducts(pageable);
    }

    /**
     * Tìm kiếm sản phẩm theo tên hoặc mô tả
     * @param name
     * @return
     */
     @GetMapping("/search")
     public ResponseEntity<List<Product>> findByNameOrDescription(@RequestParam String name) {
         return productService.findByNameOrDescription(name);
     }

    /**
     * Danh sách sản phẩm mới
     * @return
     */
    @GetMapping("/new-products")
    public ResponseEntity<List<Product>> getNewProducts() {
        return productService.getNewProducts();
    }

    /**
     * Danh sách sản phẩm theo danh mục
     * @param categoryId
     * @param pageable
     * @return
     */
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Page<Product>> findByCategoryId(
            @PathVariable Long categoryId,
            @PageableDefault(size = 10, sort = "productName") Pageable pageable) {
        return productService.findByCategoryId(categoryId, pageable);
    }

    /**
     * Chi tiết thông tin sản phẩm theo id
     * @param productId
     * @return
     * @throws DataNotFoundException
     */
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) throws DataNotFoundException {
        Optional<Product> product = productService.findById(productId);
        if (product.isEmpty()) {
            throw new DataNotFoundException("productId không tìm thấy");
        }
        return ResponseEntity.ok(product.get());
    }
}