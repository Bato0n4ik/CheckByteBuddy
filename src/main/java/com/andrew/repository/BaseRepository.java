package com.andrew.repository;

import com.andrew.entity.BaseEntity;
import lombok.Getter;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;


public abstract class BaseRepository<K extends Serializable, E extends BaseEntity<K>> implements Repository<K,E>{

    @Getter
    private final Session session;
    private final Class<E> clazz;


    protected BaseRepository(Class<E> clazz, Session session) {
        this.clazz = clazz;
        this.session = session;
    }

    @Override
    public E findById(K id) {
        return session.find(clazz, id);
    }

    @Override
    public E create(E entity) {
        session.persist(entity);
        return entity;
    }

    @Override
    public E update(E entity) {
        return session.merge(entity);
    }

    @Override
    public void delete(E entity) {
        session.remove(entity);
        session.flush();
    }

    @Override
    public List<E> findAll() {
        var criteria = session.getCriteriaBuilder().createQuery(clazz);
        var from = criteria.from(clazz);
        return session.createQuery(criteria).getResultList();
    }
}
