package com.ra.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IGenericService<T, ID> {

    Page<T> findAll(Pageable pageable);


    Optional<T> findById(ID id);

    T save(T t);

    void delete(ID id);
}
