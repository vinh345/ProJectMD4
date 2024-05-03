package com.ra.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ra.exception.DataNotFoundException;
import com.ra.exception.IdFormatException;
import com.ra.model.entity.Category;
import com.ra.service.categoryservice.ICategoryService;

@RestController("adminCategoryController")
@RequestMapping("/api.myservice.com/v1/admin/categories")
public class CategoryController {
	
	@Autowired private ICategoryService categoryService;
	
	/**
	 * Lấy về danh sách tất cả danh mục (sắp xếp và phân trang)
	 * @param pageable
	 * @return
	 */
	@GetMapping
	public ResponseEntity<Page<Category>> findAll(@PageableDefault Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAll(pageable));
	}
	
	/**
	 * Lấy về thông tin danh mục theo id
	 * @param categoryId
	 * @return
	 * @throws DataNotFoundException
	 * @throws IdFormatException
	 */
	@GetMapping("{categoryId}")
	public ResponseEntity<Category> findById(@PathVariable("categoryId") String categoryId) throws DataNotFoundException, IdFormatException {
		return categoryService.findById(categoryId);
	}
	
	/**
	 * Thêm mới danh mục
	 * @param category
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Category> create(@RequestBody Category category) {
		return categoryService.create(category);
	}

	/**
	 * Chỉnh sửa thông tin danh mục
	 * @param categoryId
	 * @param category
	 * @return
	 * @throws DataNotFoundException
	 * @throws IdFormatException
	 */
	@PutMapping("{categoryId}")
	public ResponseEntity<Category> update(
			@PathVariable("categoryId") String categoryId,
			@RequestBody Category category) throws DataNotFoundException, IdFormatException {
		return categoryService.update(categoryId, category);
	}

	/**
	 * Xóa danh mục
	 * @param categoryId
	 * @return
	 * @throws IdFormatException
	 */
	@DeleteMapping("{categoryId}")
	public ResponseEntity<Void> delete(@PathVariable("categoryId") String categoryId) throws IdFormatException {
		return categoryService.delete(categoryId);
	}
}
