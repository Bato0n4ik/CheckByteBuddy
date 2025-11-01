package com.andrew.mapper;

import com.andrew.dto.UserCreateDto;
import com.andrew.entity.User;

public class UserCreateMapper implements Mapper<UserCreateDto, User> {

    @Override
    public User map(UserCreateDto object) {

        return User.builder()
                .name(object.name())
                .password(object.password())
                .birthday(object.birthday())
                .build();
    }
}
