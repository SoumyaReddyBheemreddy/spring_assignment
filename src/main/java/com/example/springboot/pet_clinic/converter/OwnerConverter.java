package com.example.springboot.pet_clinic.converter;

import com.example.springboot.pet_clinic.dto.OwnerDTO;
import com.example.springboot.pet_clinic.entity.Owner;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OwnerConverter {
    public OwnerDTO entityToDto(Owner owner)
    {
        OwnerDTO ownerDTO = new OwnerDTO();
        ownerDTO.setId(owner.getId());
        ownerDTO.setName(owner.getName());
        ownerDTO.setPhoneNumber(owner.getPhoneNumber());
        ownerDTO.setUsers(owner.getUsers());
        ownerDTO.setPets(owner.getPets());
        return ownerDTO;
    }
    public List<OwnerDTO> entityToDto(List<Owner> owners)
    {
        List<OwnerDTO> ownersDTO = new ArrayList<>();
        for(Owner owner: owners){
            OwnerDTO ownerDTO= entityToDto(owner);
            ownersDTO.add(ownerDTO);
        }
        return  ownersDTO;
    }
    public Owner dtoToEntity(OwnerDTO ownerDTO)
    {
        ModelMapper mapper=new ModelMapper();
        return mapper.map(ownerDTO,Owner.class);
    }
}
