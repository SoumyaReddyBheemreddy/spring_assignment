package com.example.springboot.pet_clinic.service;

import com.example.springboot.pet_clinic.dao.PetRepository;
import com.example.springboot.pet_clinic.entity.Pet;
import com.example.springboot.pet_clinic.entity.Users;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@SpringBootTest
@ExtendWith(MockitoExtension.class)

class PetServiceTest {
    @InjectMocks
    PetServiceImpl petService;
    @Mock
    PetRepository petRepository;
    @Test
    void findAllTest(){
        List<Pet> pets = new ArrayList<>();
        Pet pet1 = new Pet(1,"Tom","Cat");
        Pet pet2 = new Pet(2,"Jerry","Mouse");
        Pet pet3 = new Pet(3,"Bam","Dog");
        pets.add(pet1);
        pets.add(pet2);
        pets.add(pet3);
        when(petRepository.findAll()).thenReturn(pets);
        List<Pet> petList = petService.findAll();
        assertEquals(3,petList.size());
        // to checking whether we are using repository or not in petService
        verify(petRepository,times(1)).findAll();
    }
    @Test
    void findByIdTest() throws RuntimeException{
        Optional<Pet> result = Optional.of(new Pet(1,"Tom","Cat"));
        Mockito.when(petRepository.findById(1)).thenReturn(result);
        Pet pet = petService.findById(1);
        assertEquals(1,pet.getId());
        assertEquals("Tom",pet.getName());
        assertEquals("Cat",pet.getType());
        verify(petRepository,times(1)).findById(1);
        int id = -100000;
        Optional<Pet> result1 = Optional.empty();
        when(petRepository.findById(id)).thenReturn(result1);
        try {
            petService.findById(id);
        }
        catch (RuntimeException exception){
            assertEquals("Pet id is not found - "+id,exception.getMessage());
        }
    }
    @Test
    void saveTest(){
        Pet pet = new Pet(1,"Tom","Cat");
        petService.save(pet);
        verify(petRepository,times(1)).save(pet);
    }
    @Test
    void deleteTest(){
        Pet pet = new Pet(1,"Tom","Cat");
        petService.save(pet);
        petService.deleteById(pet.getId());
        Optional<Pet> result = petRepository.findById(pet.getId());
        assertEquals(Optional.empty(),result);
    }
}
