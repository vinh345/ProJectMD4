package com.ra.controller.admin;

import com.ra.model.dto.request.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ra.exception.DataNotFoundException;
import com.ra.exception.IdFormatException;
import com.ra.model.entity.Product;
import com.ra.service.productService.IProductService;

import jakarta.validation.Valid;

@RestController("adminProductController")
@RequestMapping("/api.myservice.com/v1/admin/products")
public class ProductController {
	
	@Autowired private IProductService productService;
	
	/**
	 * Lấy về danh sách tất cả sản phẩm (sắp xếp và phân trang)
	 * @param pageable
	 * @return
	 */
	@GetMapping
	public ResponseEntity<Page<Product>> findAll(@PageableDefault Pageable pageable) {
		Page<Product> page = productService.findAll(pageable);
		return ResponseEntity.status(HttpStatus.OK).body(page);
	}
	
	/**
	 * Lấy về thông tin sản phẩm theo id
	 * @param productId
	 * @return
	 * @throws DataNotFoundException
	 */
	@GetMapping("{productId}")
	public ResponseEntity<Product> findById(@PathVariable("productId") String productId) throws DataNotFoundException {
		return productService.findById(productId);
	}
	
	/**
	 * Thêm mới sản phẩm
	 * @param request
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Product> create(@RequestBody @Valid ProductRequest request) throws DataNotFoundException {
		return productService.save(request);
	}
	
	/**
	 * Chỉnh sửa thông tin sản phẩm
	 * @param productId
	 * @param request
	 * @return
	 * @throws IdFormatException 
	 * @throws DataNotFoundException 
	 */
	@PutMapping("{productId}")
	public ResponseEntity<Product> update(
			@PathVariable("productId") String productId, 
			@RequestBody @Valid ProductRequest request) throws DataNotFoundException, IdFormatException {
		return productService.update(productId, request);
	}
	
	/**
	 * Xóa sản phẩm
	 * @param productId
	 * @return
	 */
	@DeleteMapping("{productId}")
	public ResponseEntity<Void> delete(@PathVariable("productId") String productId) throws IdFormatException {
		return productService.delete(productId);
	}
	
	
}
