package com.andrew.repository;

import com.andrew.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

public interface Repository <K extends Serializable, E extends BaseEntity<K>> {
     E findById(K id);
     E create(E entity);
     E update(E entity);
     void delete(E entity);
     List<E> findAll();
}
