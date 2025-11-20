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


public class Main {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();


        //ByteBuddy work only with PROXY on Session  class,
        // not with original Session received from sessionFactory.openSession()
        // and not with sessionFactory.getCurrentSession()
        // In all cases except for the proxy for the current session, it closed the session!!!
        Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));


        try(session){

            TransactionInterceptor myOwnInterceptor = new TransactionInterceptor(sessionFactory);

            UserRepository userRepository = new UserRepository(session);

            UserReadMapper userReadMapper = new UserReadMapper();

            UserCreateMapper userCreateMapper = new UserCreateMapper();

            var userService = new ByteBuddy()
                     .subclass(UserService.class)
                     .method(ElementMatchers.any())
                     .intercept(MethodDelegation.to(myOwnInterceptor))
                     .make()
                     .load(UserService.class.getClassLoader())
                     .getLoaded()
                     .getDeclaredConstructor(UserRepository.class, UserReadMapper.class, UserCreateMapper.class/*, Session.class*/)
                     .newInstance(userRepository, userReadMapper, userCreateMapper/*,session*/ );

            userService.create(new UserCreateDto("nikita.47@gmail.com", "1234",  LocalDate.of(2003, 1,24))).ifPresent(System.out::println);
            userService.findById(1L).ifPresentOrElse(val -> System.out.println("Found user with id: " + val.id()), () -> System.out.println("Data Base not have  user with that id"));
        }

    }
}