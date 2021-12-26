package com.example.springboot.pet_clinic.service;

import com.example.springboot.pet_clinic.dao.PetRepository;
import com.example.springboot.pet_clinic.entity.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PetServiceImpl implements PetService{
    @Autowired
    private PetRepository petRepository;
    @Override
    public List<Pet> findAll() {

        return petRepository.findAll();
    }

    @Override
    public Pet findById(int id) {
        Optional<Pet> result= petRepository.findById(id);
        Pet pet =null;
        if(result.isPresent())
            pet = result.get();
        else
            throw new RuntimeException("Pet id is not found - "+id);
        return pet;
    }

    @Override
    public void save(Pet pet) {

        petRepository.save(pet);

    }

    @Override
    public void deleteById(int id) {
        petRepository.deleteById(id);
    }
}
