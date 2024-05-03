package com.ra.service.categoryservice;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ra.exception.DataNotFoundException;
import com.ra.exception.IdFormatException;
import com.ra.model.entity.Category;
import com.ra.repository.ICategoryRepository;

@Service
public class CategoryServiceIMPL implements ICategoryService {
    @Autowired ICategoryRepository categoryRepository;

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

	@Override
	public ResponseEntity<Category> findById(String categoryId) throws DataNotFoundException, IdFormatException {
		try {
			Long id = Long.valueOf(categoryId);
			Optional<Category> category = findById(id);
			if (category.isEmpty()) {
				throw new DataNotFoundException("categoryId không tồn tại");
			}
			return ResponseEntity.status(HttpStatus.OK).body(category.get());
		} catch (NumberFormatException e) {
			throw new IdFormatException("categoryId không phải số");
		}
	}

	@Override
	public ResponseEntity<Category> create(Category category) {
		return ResponseEntity.status(HttpStatus.CREATED).body(categoryRepository.save(category));
	}

	@Override
	public ResponseEntity<Category> update(String categoryId, Category category) throws DataNotFoundException, IdFormatException {
		try {
			Long id = Long.valueOf(categoryId);
			Optional<Category> optional = findById(id);
			if (optional.isEmpty()) {
				throw new DataNotFoundException("categoryId không tồn tại");
			}
			optional.get().setCategoryName(category.getCategoryName());
			optional.get().setDescription(category.getDescription());
			optional.get().setStatus(category.getStatus());
			save(optional.get());
			return ResponseEntity.status(HttpStatus.OK).body(optional.get());
		} catch (NumberFormatException e) {
			throw new IdFormatException("categoryId không phải số");
		}
	}

	@Override
	public ResponseEntity<Void> delete(String categoryId) throws IdFormatException {
		try {
			Long id = Long.valueOf(categoryId);
			categoryRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (NumberFormatException e) {
			throw new IdFormatException("categoryId không phải số");
		}
	}
}
