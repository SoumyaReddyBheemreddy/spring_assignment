package com.example.springboot.pet_clinic.dao;

import com.example.springboot.pet_clinic.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, String> {
}
