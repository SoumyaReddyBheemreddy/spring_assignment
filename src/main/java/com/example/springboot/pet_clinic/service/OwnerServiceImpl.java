package com.example.springboot.pet_clinic.service;

import com.example.springboot.pet_clinic.dao.OwnerRepository;
import com.example.springboot.pet_clinic.dao.PetRepository;
import com.example.springboot.pet_clinic.entity.Authorities;
import com.example.springboot.pet_clinic.entity.Owner;
import com.example.springboot.pet_clinic.entity.Pet;
import com.example.springboot.pet_clinic.entity.Users;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Log4j2
public class OwnerServiceImpl implements OwnerService{
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private PetRepository petRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }
    @Override
    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }

    @Override
    public Owner findById(int id) {
        Optional<Owner> result = ownerRepository.findById(id);
        Owner owner = null;
        if(result.isEmpty()){
            log.error("Invalid Owner id -  "+id);
            throw new RuntimeException("Owner id is not found - " + id);

        }
        owner = result.get();
        return owner;
    }

    @Override
    public Optional<Owner> findByNameAndPhoneNumber(String name, String phoneNumber) {
        return ownerRepository.findByNameAndPhoneNumber(name,phoneNumber);
    }


    @Override
    public void save(Owner owner) {
        ownerRepository.save(owner);
    }

    @Override
    public void savePetOwner(Owner owner, Pet pet) {
        Users user = new Users();
        user.setUserName(owner.getName());
        user.setPassword((new BCryptPasswordEncoder().encode(owner.getName())));
        user.setEnabled(1);
        user.setOwner(owner);
        Authorities authorities = new Authorities();
        authorities.setRole("ROLE_OWNER");
        authorities.setUser(user);
        user.addAuthorities(authorities);
        owner.setUsers(user);
        Optional<Owner> result = findByNameAndPhoneNumber(owner.getName(),owner.getPhoneNumber());
        if(result.isPresent())
            owner = result.get();
        else
            save(owner);
        if(!pet.getOwners().contains(owner)){
            pet.addOwner(owner);
            petRepository.save(pet);
        }
    }


    @Override
    public void deleteById(int id) {
        ownerRepository.deleteById(id);
    }
}
