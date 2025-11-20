package com.andrew.service;

import com.andrew.dto.UserCreateDto;
import com.andrew.dto.UserReadDto;
import com.andrew.entity.User;
import com.andrew.mapper.UserCreateMapper;
import com.andrew.mapper.UserReadMapper;
import com.andrew.repository.UserRepository;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.UUID;


public class UserService {


    private final UserReadMapper userReadMapper;
    private final UserCreateMapper userCreateMapper;
    private final UserRepository userRepository;
    //private final Session session;


    public UserService(UserRepository userRepository, UserReadMapper userReadMapper, UserCreateMapper userCreateMapper/*, Session session*/) {
        this.userRepository = userRepository;
        this.userReadMapper = userReadMapper;
        this.userCreateMapper = userCreateMapper;
        //this.session = session;
    }

    @Transactional
    public Optional<UserReadDto> findById (Long id) {

        //Transaction transaction = null;
        //Optional<UserReadDto> user = Optional.empty();
        //try(session) {
        //    transaction = session.beginTransaction();
//
        //    user = Optional.of(userRepository.findById(id)).map(userReadMapper::map);
//
        //    transaction.commit();
        //}
        //catch (Exception e) {
        //    if(transaction != null) {
        //        transaction.rollback();
        //    }
        //}
//
        //return user;

        return Optional.ofNullable(userRepository.findById(id)).map(userReadMapper::map);
    }

    @Transactional
    public Optional<UserReadDto>  create(UserCreateDto userDto) {
        //User user = userCreateMapper.map(userDto);
        //Optional<UserReadDto> dto = Optional.empty();
        //Transaction transaction = null;
        //try(session) {
        //    transaction = session.getTransaction();
        //    transaction.begin();
//
        //    dto = Optional.of(userRepository.create(user)).map(userReadMapper::map);
//
        //    transaction.commit();
        //}
        //catch (Exception e) {
        //    if(transaction != null) {
        //        transaction.rollback();
        //    }
        //}
//
//
        //return dto;


        User user = userCreateMapper.map(userDto);
        return Optional.of(userRepository.create(user)).map(userReadMapper::map);

    }
}
