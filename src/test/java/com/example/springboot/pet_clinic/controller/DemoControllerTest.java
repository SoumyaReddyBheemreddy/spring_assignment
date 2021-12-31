package com.example.springboot.pet_clinic.controller;

import com.example.springboot.pet_clinic.config.TestDataSourceConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = TestDataSourceConfig.class)
@TestPropertySource(locations = "classpath:applicationTest.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DemoControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @BeforeAll
    private void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    void homeTest() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(MockMvcResultMatchers.view().name("home/welcome"));
    }
}
