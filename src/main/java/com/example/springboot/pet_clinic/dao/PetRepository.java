package com.example.springboot.pet_clinic.dao;

import com.example.springboot.pet_clinic.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface PetRepository extends JpaRepository<Pet,Integer> {

}
