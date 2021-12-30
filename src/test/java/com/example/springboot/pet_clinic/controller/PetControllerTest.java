package com.example.springboot.pet_clinic.controller;

import com.example.springboot.pet_clinic.config.TestDataSourceConfig;
import com.example.springboot.pet_clinic.entity.Pet;
import com.example.springboot.pet_clinic.service.PetService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = TestDataSourceConfig.class)
@TestPropertySource(locations = "classpath:applicationTest.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PetControllerTest {
    @MockBean
    private PetService petService;
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @BeforeAll
    private void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    void listPetsTest() throws Exception{
        List<Pet> pets = new ArrayList<>();
        Pet pet1 = new Pet(1,"Tom","Cat");
        Pet pet2 = new Pet(2,"Jerry","Mouse");
        pets.add(pet1);
        pets.add(pet2);
        when(petService.findAll()).thenReturn(pets);
        mockMvc.perform(get("/pets/list")).andExpect(status().isOk()).andExpect(view().name("pets/listPets"));
        verify(petService,times(1)).findAll();
    }
    @Test
    void addPetTest() throws Exception{
        Pet pet = new Pet();
        mockMvc.perform(get("/pets/form").flashAttr("pet",pet))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/petForm"));

    }
    @Test
    void savePetTest() throws Exception{
        Pet pet = new Pet(0,"Tom","Cat");
        mockMvc.perform(post("/pets/save").flashAttr("pet",pet)).andExpect(view().name("redirect:/pets/list"));
        if(pet.getId()!=0){
            Pet tempPet = petService.findById(pet.getId());
            tempPet.setName(pet.getName());
            tempPet.setType(pet.getType());
            verify(petService,times(1)).save(tempPet);
        }
        verify(petService,times(1)).save(pet);
    }
    @Test
    void updatePetTest() throws Exception{
        Pet pet = new Pet(1,"Tom","Cat");
       when(petService.findById(1)).thenReturn(pet);
       mockMvc.perform(get("/pets/update?petId="+pet.getId())).andExpect(status().isOk())
               .andExpect(view().name("pets/petForm"));
    }
    @Test
    void deletePetTest() throws Exception{
        Pet pet = new Pet(1,"Tom","Cat");
        when(petService.findById(1)).thenReturn(pet);
        mockMvc.perform(get("/pets/delete?petId="+pet.getId()))
                .andExpect(view().name("redirect:/pets/list"));
        verify(petService,times(1)).deleteById(pet.getId());
    }
}
