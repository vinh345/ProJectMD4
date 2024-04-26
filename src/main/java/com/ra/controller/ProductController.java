package com.ra.controller;

import com.ra.exception.DataNotFoundException;
import com.ra.exception.NoDataException;
import com.ra.model.dto.response.PageResponse;
import com.ra.model.entity.Product;
import com.ra.service.productservice.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api.myservice.com/v1/products")
public class ProductController {

    @Autowired
    private IProductService productService;


    // lấy ra danh sách sản phẩm có phân trang
    @GetMapping
    public ResponseEntity<PageResponse> findAllProducts(@PageableDefault Pageable pageable) throws NoDataException {
        Page<Product> productPage= productService.findAll(pageable);
        PageResponse response= PageResponse.builder()
                .data(productPage.getContent())
                .totalPages(productPage.getTotalPages())
                .last(productPage.isLast())
                .first(productPage.isFirst())
                .number(productPage.getNumber())
                .size(productPage.getSize())
                .sort(productPage.getSort())
                .totalElements(productPage.getNumberOfElements())
                .build();
       return new ResponseEntity<>(response, HttpStatus.OK);
    }


     //Tìm kiếm sản phẩm theo tên hoặc mô tả
     @GetMapping("/search")
     public ResponseEntity<List<Product>> findByNameOrDescription(@RequestParam String name) throws NoDataException {
         List<Product> products = productService.search(name);
         return new ResponseEntity<>(products, HttpStatus.OK);
     }

    // Lấy ra danh sách sản phẩm theo danh mục
    @GetMapping("/categories/{categoryId}")
    public Page<Product> findByCategoryId(
            @PathVariable Long categoryId,
            @PageableDefault(size = 10, sort = "productName") Pageable pageable) {
        return productService.findByCategoryId(categoryId, pageable);
    }


    //
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) throws DataNotFoundException {
            return ResponseEntity.ok(productService.findById(productId));
    }

    // new product
    @GetMapping("/new-products")
    public ResponseEntity<List<Product>> getNewProducts() {
        List<Product> newProducts = productService.getNewProducts();
        return ResponseEntity.ok(newProducts);
    }


}