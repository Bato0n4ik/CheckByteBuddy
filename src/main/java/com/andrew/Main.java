package com.andrew;

import com.andrew.Interceptors.TransactionInterceptor;
import com.andrew.dto.UserCreateDto;
import com.andrew.mapper.UserCreateMapper;
import com.andrew.mapper.UserReadMapper;
import com.andrew.repository.UserRepository;
import com.andrew.service.UserService;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;


public class Main {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();



        Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class<?>[]{Session.class},(proxy, method, args1) -> {
            return method.invoke(sessionFactory.openSession(), args1);
        });

        TransactionInterceptor myOwnInterceptor = new TransactionInterceptor(sessionFactory);

        UserRepository userRepository = new UserRepository(session);

        UserReadMapper userMapper = new UserReadMapper();

        UserCreateMapper userCreateMapper = new UserCreateMapper();

        var userService = new ByteBuddy()
                .subclass(UserService.class)
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(myOwnInterceptor))
                .make()
                .load(UserService.class.getClassLoader())
                .getLoaded()
                .getDeclaredConstructor(UserRepository.class, UserReadMapper.class, UserCreateMapper.class)
                .newInstance(userRepository, userMapper, userCreateMapper);

        System.out.println();


        userService.create(new UserCreateDto("bato0n4ik.47@gmail.com", "12345", LocalDate.of(1998, 9,1))).ifPresent(System.out::println);

        System.out.println();

        userService.findById(1L).ifPresent(System.out::println);

    }
}