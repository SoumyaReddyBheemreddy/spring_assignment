package com.example.springboot.pet_clinic.controller;


import com.example.springboot.pet_clinic.config.TestDataSourceConfig;
import com.example.springboot.pet_clinic.converter.AppointmentConverter;
import com.example.springboot.pet_clinic.converter.PetConverter;
import com.example.springboot.pet_clinic.entity.Appointment;
import com.example.springboot.pet_clinic.entity.Pet;
import com.example.springboot.pet_clinic.service.AppointmentService;
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


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = TestDataSourceConfig.class)
@TestPropertySource(locations = "classpath:applicationTest.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class AppointmentControllerTest {
    @MockBean
    private PetService petService;

    @MockBean
    private AppointmentService appointmentService;
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private  final AppointmentConverter appointmentConverter =new AppointmentConverter();
    private final PetConverter petConverter = new PetConverter();
    @BeforeAll
    private void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    void listAppointmentsTest() throws Exception{
        Pet pet = new Pet(1,"Tom","Cat");
        Appointment appointment = new Appointment(1,"2021-12-30","description",400.0,"Raj","specialization");
        pet.addAppointment(appointment);
        when(petService.findById(pet.getId())).thenReturn(pet);
        mockMvc.perform(get("/appointments/list?petId="+pet.getId())
                .flashAttr("pet",pet)
                .flashAttr("appointments",pet.getAppointments()))
                .andExpect(view().name("appointments/getPetAppointments"));
    }
    @Test
    void addAppointmentTest() throws Exception{
        Pet pet = new Pet(1,"Tom","Cat");
        when(petService.findById(pet.getId())).thenReturn(pet);
        Appointment appointment = new Appointment();
        mockMvc.perform(get("/appointments/show?petId="+pet.getId())
                        .flashAttr("pet",pet)
                        .flashAttr("appointment",appointment))
                .andExpect(status().isOk())
                .andExpect(view().name("appointments/appointmentForm"));

    }
    @Test
    void saveAppointmentTest() throws Exception{
        Pet pet = new Pet(1,"Tom","Cat");
        when(petService.findById(pet.getId())).thenReturn(pet);
        Appointment appointment = new Appointment(0,"2021-12-30","description",400.0,"Raj","specialization");
        appointment.setPet(pet);
        mockMvc.perform(post("/appointments/save?petId="+pet.getId()))
                .andExpect(view().name("appointments/appointmentForm"));
        mockMvc.perform(post("/appointments/save?petId="+pet.getId())
                .flashAttr("pet",petConverter.entityToDto(pet))
                        .flashAttr("appointment",appointmentConverter.entityToDto(appointment)))
                .andExpect(view().name("redirect:/appointments/list?petId="+pet.getId()));

    }
    @Test
   void updateAppointmentTest() throws Exception{
        Appointment appointment = new Appointment(1,"2021-12-30","description",400.0,"Raj","specialization");
        Pet pet = new Pet(1,"Tom","Cat");
        when(petService.findById(pet.getId())).thenReturn(pet);
        when(appointmentService.findById(appointment.getId())).thenReturn(appointment);
        mockMvc.perform(get("/appointments/update?petId="+pet.getId()+"&appointmentId="+appointment.getId())
                        .flashAttr("pet",pet)
                        .flashAttr("appointment",appointment))
                .andExpect(status().isOk())
                .andExpect(view().name("appointments/appointmentForm"));
    }
    @Test
    void deleteAppointmentTest() throws Exception{
        Pet pet = new Pet(1,"Tom","Cat");
        Appointment appointment = new Appointment(1,"2021-12-30","description",400.0,"Raj","specialization");
        when(appointmentService.findById(1)).thenReturn(appointment);
        mockMvc.perform(get("/appointments/delete?petId="+pet.getId()+"&appointmentId="+appointment.getId()))
                .andExpect(view().name("redirect:/appointments/list?petId=1"));
        verify(appointmentService,times(1)).deleteById(appointment.getId());
    }
}
