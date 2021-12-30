package com.example.springboot.pet_clinic.controller;

import com.example.springboot.pet_clinic.config.TestDataSourceConfig;
import com.example.springboot.pet_clinic.entity.Pet;
import com.example.springboot.pet_clinic.service.PetService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = TestDataSourceConfig.class)
@TestPropertySource(locations = "classpath:applicationTest.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PetControllerTest {
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
    void listPets() throws Exception{
        List<Pet> pets = new ArrayList<>();
        Pet pet1 = new Pet(1,"Tom","Cat");
        Pet pet2 = new Pet(2,"Jerry","Mouse");
        pets.add(pet1);
        pets.add(pet2);
        when(petService.findAll()).thenReturn(pets);
        mockMvc.perform(get("/pets/list")).andExpect(status().isOk());
    }
}
