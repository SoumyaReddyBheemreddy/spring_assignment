package com.example.springboot.pet_clinic.service;

import com.example.springboot.pet_clinic.dao.OwnerRepository;
import com.example.springboot.pet_clinic.entity.Owner;
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

class OwnerServiceTest {
    @InjectMocks
    OwnerServiceImpl ownerService;
    @Mock
    OwnerRepository ownerRepository;
    @Test
    void findAllTest(){
        List<Owner> owners= new ArrayList<>();
        Owner owner1 = new Owner(1,"John","9876543210");
        Owner owner2 = new Owner(2,"Sam","8765432109");
        Owner owner3 = new Owner(3,"Chris","7654321098");
        owners.add(owner1);
        owners.add(owner2);
        owners.add(owner3);
        when(ownerRepository.findAll()).thenReturn(owners);
        List<Owner> ownerList = ownerRepository.findAll();
        assertEquals(3,ownerList.size());
        // to checking whether we are using repository or not in petService
        verify(ownerRepository,times(1)).findAll();
    }
    @Test
    void findByIdTest(){
        Optional<Owner> result = Optional.of(new Owner(1,"John","9876543210"));
        Mockito.when(ownerRepository.findById(1)).thenReturn(result);
        Owner owner = ownerService.findById(1);
        assertEquals(1,owner.getId());
        assertEquals("John",owner.getName());
        assertEquals("9876543210",owner.getPhoneNumber());
        verify(ownerRepository,times(1)).findById(1);
    }
    @Test
    void saveTest(){
        Owner owner = new Owner(1,"John","9876543210");
        ownerService.save(owner);
        verify(ownerRepository,times(1)).save(owner);
    }
    @Test
    void deleteTest(){
        Owner owner = new Owner(1,"John","9876543210");
        ownerService.save(owner);
        ownerService.deleteById(owner.getId());
        Optional<Owner> result = ownerRepository.findById(owner.getId());
        assertEquals(Optional.empty(),result);
    }
}
