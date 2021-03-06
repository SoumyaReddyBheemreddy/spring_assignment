package com.example.springboot.pet_clinic.service;

import com.example.springboot.pet_clinic.dao.UserRepository;
import com.example.springboot.pet_clinic.entity.Users;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@Log4j2
public class UsersServiceImpl implements UsersService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public Users findById(String name) {
        Optional<Users> result = userRepository.findById(name);
        Users users = null;
        if(result.isEmpty()){
            log.error("Invalid User name - "+ name);
            throw new RuntimeException("Invalid user name - "+ name);
        }

        users = result.get();
        return users;
    }

    @Override
    public void save(Users user) {
        userRepository.save(user);
    }
}
