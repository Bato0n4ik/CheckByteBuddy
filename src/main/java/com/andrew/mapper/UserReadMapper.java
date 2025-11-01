package com.andrew.mapper;

import com.andrew.dto.UserReadDto;
import com.andrew.entity.User;


public class UserReadMapper implements Mapper<User, UserReadDto> {

    public UserReadDto map(User user) {

        return new UserReadDto(user.getId(), user.getName(), user.getPassword(), user.getBirthday());
    }
}
