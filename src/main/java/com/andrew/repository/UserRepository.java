package com.andrew.repository;

import com.andrew.entity.User;
import jakarta.persistence.EntityManager;


public class UserRepository {

    private final EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public User findById (Long id) {
        return entityManager.find(User.class, id);
    }

    public User create(User user) {
        entityManager.persist(user);
        return user;
    }
}
