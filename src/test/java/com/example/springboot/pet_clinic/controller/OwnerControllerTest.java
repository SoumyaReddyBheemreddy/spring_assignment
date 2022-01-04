package com.example.springboot.pet_clinic.controller;

import com.example.springboot.pet_clinic.config.TestDataSourceConfig;
import com.example.springboot.pet_clinic.converter.OwnerConverter;
import com.example.springboot.pet_clinic.converter.PetConverter;
import com.example.springboot.pet_clinic.entity.Owner;
import com.example.springboot.pet_clinic.entity.Pet;

import com.example.springboot.pet_clinic.service.OwnerService;
import com.example.springboot.pet_clinic.service.PetService;
import com.example.springboot.pet_clinic.service.UsersService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = TestDataSourceConfig.class)
@TestPropertySource(locations = "classpath:applicationTest.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class OwnerControllerTest {
        @MockBean
        private PetService petService;
        @MockBean
        private UsersService usersService;
        @MockBean
        private OwnerService ownerService;
        private MockMvc mockMvc;
        @Autowired
        private WebApplicationContext webApplicationContext;

        private final PetConverter petConverter  = new PetConverter();

        private final OwnerConverter ownerConverter = new OwnerConverter();
        @BeforeAll
        private void setup(){
            mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        }
        @Test
        void listPetsTest() throws Exception{

            Pet pet = new Pet(1,"Tom","Cat");
            Owner owner1 = new Owner(1,"john","9876543210");
           pet.addOwner(owner1);
           when(petService.findById(pet.getId())).thenReturn(pet);
            mockMvc.perform(get("/owners/list?petId="+pet.getId()).flashAttr("pet",pet)
                            .flashAttr("owners",pet.getOwners()))
                    .andExpect(status().isOk()).andExpect(view().name("owners/getPetOwners"));

        }
    @Test
    void addPetTest() throws Exception{
        Pet pet = new Pet(1,"Tom","Cat");
        when(petService.findById(pet.getId())).thenReturn(pet);
        Owner owner = new Owner();
        mockMvc.perform(get("/owners/form?petId="+pet.getId()).flashAttr("pet",pet)
                        .flashAttr("owner",owner))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerForm"));
    }
    @Test
    void savePetOwnerTest() throws Exception{
        Pet pet = new Pet(1,"Tom","Cat");
        Owner owner = new Owner(0,"John","9876543210");
        when(petService.findById(pet.getId())).thenReturn(pet);
        mockMvc.perform(post("/owners/save?petId="+pet.getId()).flashAttr("pet",petConverter.entityToDto(pet))
                .flashAttr("owner",ownerConverter.entityToDto(owner)))
                .andExpect(view().name("redirect:/owners/list?petId=1"));
        mockMvc.perform(post("/owners/save?petId="+pet.getId()))
                .andExpect(view().name("owners/ownerForm"));
    }
    @Test
    void updatePetOwnerTest() throws Exception{
        Pet pet = new Pet(1,"Tom","Cat");
        Owner owner = new Owner(1,"John","9876543210");
        when(petService.findById(pet.getId())).thenReturn(pet);
        when(ownerService.findById(owner.getId())).thenReturn(owner);
        mockMvc.perform(get("/owners/update?petId="+pet.getId()+"&ownerId="+owner.getId()).flashAttr("pet",pet)
                .flashAttr("owner",owner)).andExpect(view().name("owners/updateOwnerForm"));

    }
    @Test
    void updateOwnerTest() throws Exception{
        Pet pet = new Pet(1,"Tom","Cat");
        Owner owner = new Owner(1,"John","9876543210");
        when(ownerService.findById(owner.getId())).thenReturn(owner);
        mockMvc.perform(post("/owners/save-update?petId="+pet.getId())
                        .flashAttr("pet",petConverter.entityToDto(pet))
                .flashAttr("owner",ownerConverter.entityToDto(owner)))
                .andExpect(view().name("redirect:/owners/list?petId=1"));
        mockMvc.perform(post("/owners/save-update?petId="+pet.getId()))
                .andExpect(view().name("owners/ownerForm"));
    }
    @Test
    void deletePetTest() throws Exception{
        Pet pet = new Pet(1,"Tom","Cat");
        Owner owner = new Owner(1,"John","9876543210");
        when(ownerService.findById(1)).thenReturn(owner);
        mockMvc.perform(get("/owners/delete?petId="+pet.getId()+"&ownerId="+owner.getId()))
                .andExpect(view().name("redirect:/owners/list?petId=1"));

    }
}
