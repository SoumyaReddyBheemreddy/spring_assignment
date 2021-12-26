package com.example.springboot.pet_clinic.service;

import com.example.springboot.pet_clinic.entity.Pet;

import java.util.List;

public interface PetService {
    public List<Pet> findAll();
    public Pet findById(int id);
    public void save(Pet pet);
    public void deleteById(int id);
}
