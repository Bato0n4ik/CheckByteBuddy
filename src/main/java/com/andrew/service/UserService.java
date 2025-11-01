package com.andrew.service;

import com.andrew.dto.UserCreateDto;
import com.andrew.dto.UserReadDto;
import com.andrew.entity.User;
import com.andrew.mapper.UserCreateMapper;
import com.andrew.mapper.UserReadMapper;
import com.andrew.repository.UserRepository;
import jakarta.transaction.Transactional;

import java.util.Optional;


public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateMapper userCreateMapper;

    public UserService(UserRepository userRepository, UserReadMapper userReadMapper, UserCreateMapper userCreateMapper) {
        this.userRepository = userRepository;
        this.userReadMapper = userReadMapper;
        this.userCreateMapper = userCreateMapper;
    }

    @Transactional
    public Optional<UserReadDto> findById (Long id) {
        //map without validation, need validate resul after call userRepository.findById()
        return Optional.ofNullable(userRepository.findById(id)).map(userReadMapper::map);
    }

    @Transactional
    public Optional<UserReadDto> create(UserCreateDto userDto) {
        User user = userCreateMapper.map(userDto);
        return Optional.ofNullable(userRepository.create(user)).map(userReadMapper::map);
    }
}
