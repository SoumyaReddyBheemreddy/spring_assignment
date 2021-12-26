package com.example.springboot.pet_clinic.service;

import com.example.springboot.pet_clinic.entity.Owner;
import com.example.springboot.pet_clinic.entity.Pet;

import java.util.List;
import java.util.Optional;

public interface OwnerService {
    public List<Owner> findAll();
    public Owner findById(int id);
    public Optional<Owner> findByNameAndPhoneNumber(String name, String phoneNumber);
    public void save(Owner owner);
    public void savePetOwner(Owner owner, Pet pet);
    public void deleteById(int id);
}
