package com.example.springboot.pet_clinic.converter;

import com.example.springboot.pet_clinic.dto.PetDTO;
import com.example.springboot.pet_clinic.entity.Pet;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class PetConverter {
    public PetDTO entityToDto(Pet pet)
    {
        ModelMapper mapper=new ModelMapper();
        PetDTO map=mapper.map(pet,PetDTO.class);
        return map;
    }
    public List<PetDTO> entityToDto(List<Pet> pets)
    {
        return  pets.stream().map(pet->entityToDto(pet)).collect(Collectors.toList());

    }
}
