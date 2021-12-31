package com.example.springboot.pet_clinic.service;


import com.example.springboot.pet_clinic.dao.UserRepository;
import com.example.springboot.pet_clinic.entity.Appointment;
import com.example.springboot.pet_clinic.entity.Users;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@SpringBootTest
@ExtendWith(MockitoExtension.class)

class UserServiceTest {
    @InjectMocks
    UsersServiceImpl userService;
    @Mock
    UserRepository userRepository;

    @Test
    void findByIdTest() throws RuntimeException{
        Optional<Users> result = Optional.of(new Users("Sam","Sam",1));
        Mockito.when(userRepository.findById("Sam")).thenReturn(result);
        Users user = userService.findById("Sam");
        assertEquals("Sam",user.getUserName());
        assertEquals("Sam",user.getPassword());
        verify(userRepository,times(1)).findById("Sam");
        String name = "afeafwe";
        Optional<Users> result1 = Optional.empty();
        when(userRepository.findById(name)).thenReturn(result1);
        try {
            userService.findById(name);
        }
        catch (RuntimeException exception){
            assertEquals("Invalid user name - "+ name,exception.getMessage());
        }
    }

    @Test
    void saveTest(){
        Users user = new Users();
        user.setUserName("Sam");
        user.setPassword("Sam");
        userService.save(user);
        verify(userRepository,times(1)).save(user);
    }
}
