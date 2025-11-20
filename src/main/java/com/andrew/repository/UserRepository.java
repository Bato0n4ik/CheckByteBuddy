package com.andrew.repository;

import com.andrew.entity.User;
import org.hibernate.Session;


public class UserRepository extends BaseRepository<Long, User>{

    public UserRepository(Session session) {
        super(User.class, session);
    }
}
