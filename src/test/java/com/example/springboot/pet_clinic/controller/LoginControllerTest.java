package com.example.springboot.pet_clinic.controller;

import com.example.springboot.pet_clinic.config.TestDataSourceConfig;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = TestDataSourceConfig.class)
@TestPropertySource(locations = "classpath:applicationTest.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginControllerTest {
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
    void redirectTest() throws Exception {
        mockMvc.perform(get("/")).andExpect(redirectedUrl("/show-login-page"));
    }
    @Test
    void showLoginPageTest() throws Exception {
        mockMvc.perform(get("/show-login-page")).andExpect(view().name("fancy-login"));
    }
    @Test
    void accessDeniedTest() throws Exception {
        mockMvc.perform(get("/access-denied")).andExpect(view().name("access-denied"));
    }
}
