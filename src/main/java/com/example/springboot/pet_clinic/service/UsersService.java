package com.example.springboot.pet_clinic.service;

import com.example.springboot.pet_clinic.entity.Users;

public interface UsersService {
    public Users findById(String name);
    public void save(Users user);
}
