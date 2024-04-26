package com.ra.service;

import com.ra.exception.NoDataException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IGenericService<T, ID> {
    Page<T> findAll (Pageable pageable) throws NoDataException;

    Optional<T> findById (ID id);

    T save (T t);
    void remove (ID id);
}
