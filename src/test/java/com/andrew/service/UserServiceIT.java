package com.andrew.service;

import com.andrew.TestBase;
import com.andrew.dto.UserCreateDto;
import com.andrew.dto.UserReadDto;
import com.andrew.entity.User;
import com.andrew.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class UserServiceIT implements TestBase {


    //private final UserReadDto FIRST_SEARCH_USER =  new UserReadDto(1L,"bato0n4ik.47@gmail.com", "12345",LocalDate.of(1998, 9,1));
    private final UserReadDto FIRST_SEARCH_USER =   new UserReadDto(1L,"bato0n4ik.47@gmail.com", "12345",LocalDate.of(1998, 9,1));
    //private final UserReadDto SECOND_SEARCH_USER = new UserReadDto(2L,"nikita.47@gmail.com", "1234", LocalDate.of(2003, 1,24));

    private final User USER_FOR_REPOSITORY_ADD = new User(4L, "Anusay-Minusay@gmail.com", "1234567", LocalDate.of(2005, 5,12));
    private final UserCreateDto USER_FOR_SERVICE_ADD = new UserCreateDto( "Anusay-Minusay@gmail.com", "1234567", LocalDate.of(2005, 5,12));

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void findById() {

        User expectedFirstUser = new User(1L,"bato0n4ik.47@gmail.com", "12345",LocalDate.of(1998, 9,1));

        Mockito.doReturn(expectedFirstUser).when(userRepository).findById(Mockito.any());
        //Mockito.when(userRepository.findById(Mockito.any())).thenReturn(ArgumentMatchers.any());

        userService.findById(1L).ifPresent(actual -> Assertions.assertEquals(FIRST_SEARCH_USER, actual));

        //userService.findById(2L).ifPresent(actual -> Assertions.assertEquals(SECOND_SEARCH_USER, actual));

        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.any());
    }



    @Test
    public void testSaveUser() {

        UserReadDto expectedUserReadDto = new UserReadDto(4L, "Anusay-Minusay@gmail.com", "1234567", LocalDate.of(2005, 5,12));

        Mockito.doReturn(USER_FOR_REPOSITORY_ADD).when(userRepository).create(USER_FOR_REPOSITORY_ADD);

        userService.create(USER_FOR_SERVICE_ADD).ifPresent(actual -> Assertions.assertEquals(expectedUserReadDto, actual));

        Mockito.verify(userRepository, Mockito.times(1)).create(USER_FOR_REPOSITORY_ADD);
    }



}
