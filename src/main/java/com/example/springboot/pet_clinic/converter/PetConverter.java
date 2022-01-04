package com.example.springboot.pet_clinic.converter;

import com.example.springboot.pet_clinic.dto.PetDTO;
import com.example.springboot.pet_clinic.entity.Pet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class PetConverter {
    public PetDTO entityToDto(Pet pet)
    {
        PetDTO petDTO = new PetDTO();
        petDTO.setName(pet.getName());
        petDTO.setId(pet.getId());
        petDTO.setAppointments(pet.getAppointments());
        petDTO.setType(pet.getType());
        petDTO.setOwners(pet.getOwners());
        return petDTO;
    }
    public List<PetDTO> entityToDto(List<Pet> pets)
    {
        List<PetDTO> petsDTO = new ArrayList<>();
        for(Pet pet: pets){
            PetDTO petDTO= entityToDto(pet);
            petsDTO.add(petDTO);
        }
        return  petsDTO;

    }
}
