package com.ra.service.categoryservice;

import org.springframework.http.ResponseEntity;

import com.ra.exception.DataNotFoundException;
import com.ra.exception.IdFormatException;
import com.ra.model.entity.Category;
import com.ra.service.IGenericService;
import org.springframework.web.bind.annotation.PathVariable;


public interface ICategoryService extends IGenericService<Category,Long> {
	
	ResponseEntity<Category> findById(String categoryId) throws DataNotFoundException, IdFormatException;

	ResponseEntity<Category> create(Category category);
	
	ResponseEntity<Category> update(String categoryId, Category category) throws DataNotFoundException, IdFormatException;

	ResponseEntity<Void> delete(String categoryId) throws IdFormatException;
}
