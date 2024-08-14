package com.example.nerdysoft_java_test.service;

import com.example.nerdysoft_java_test.entity.BaseEntity;

import java.util.Collection;
import java.util.Collections;

public interface CrudService<E extends BaseEntity> {
    void create(E entity);

    void update(E entity);

    void delete(Long id);

    E findById(Long id);

    Collection<E> findAll();
}
