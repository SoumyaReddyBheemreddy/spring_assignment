package com.example.springboot.pet_clinic.dao;
import com.example.springboot.pet_clinic.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface OwnerRepository extends JpaRepository<Owner,Integer> {


    Optional<Owner> findByNameAndPhoneNumber(String name,String phoneNumber);
}
