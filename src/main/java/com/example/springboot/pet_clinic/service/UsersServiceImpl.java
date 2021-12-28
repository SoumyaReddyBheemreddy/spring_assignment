package com.example.springboot.pet_clinic.service;

import com.example.springboot.pet_clinic.dao.UserRepository;
import com.example.springboot.pet_clinic.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UsersServiceImpl implements UsersService{
    @Autowired
    private UserRepository userRepository;
    Logger logger = Logger.getLogger(getClass().getName());
    @Override
    public Users findById(String name) {
        Optional<Users> result = userRepository.findById(name);
        Users users = null;
        if(result.isEmpty()){
            logger.warning("Invalid User name - "+ name);
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
