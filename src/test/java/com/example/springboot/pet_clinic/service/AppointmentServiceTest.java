package com.example.springboot.pet_clinic.service;


import com.example.springboot.pet_clinic.dao.AppointmentRepository;
import com.example.springboot.pet_clinic.entity.Appointment;
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

class AppointmentServiceTest {
    @InjectMocks
    AppointmentServiceImpl appointmentService;
    @Mock
    AppointmentRepository appointmentRepository;
    @Test
    void findAllTest(){
        List<Appointment> appointments= new ArrayList<>();
        Appointment appointment1 = new Appointment(1,"2021-11-21","description",500,"Mary","specialization");
        Appointment appointment2 = new Appointment(2,"2021-12-25","description",600,"Raj","specialization");
        appointments.add(appointment1);
        appointments.add(appointment2);
        when(appointmentRepository.findAll()).thenReturn(appointments);
        List<Appointment> ownerList = appointmentService.findAll();
        assertEquals(2,ownerList.size());
        // to checking whether we are using repository or not in petService
        verify(appointmentRepository,times(1)).findAll();
    }
    @Test
    void findByIdTest() throws RuntimeException{
        Optional<Appointment> result = Optional.of(new Appointment(1,"2021-11-21","description",500,"Mary","specialization"));
        Mockito.when(appointmentRepository.findById(1)).thenReturn(result);
        Appointment appointment = appointmentService.findById(1);
        assertEquals(1,appointment.getId());
        assertEquals("2021-11-21",appointment.getDate());
        assertEquals("description",appointment.getDescription());
        assertEquals(500.0,appointment.getAmount());
        assertEquals("Mary",appointment.getVetName());
        assertEquals("specialization",appointment.getSpecialization());
        verify(appointmentRepository,times(1)).findById(1);
    }
    @Test
    void saveTest(){
        Appointment appointment = new Appointment(1,"2021-11-21","description",500,"Mary","specialization");
        appointmentService.save(appointment);
        verify(appointmentRepository,times(1)).save(appointment);
    }
    @Test
    void deleteTest(){
        Appointment appointment = new Appointment(1,"2021-11-21","description",500,"Mary","specialization");
        appointmentService.save(appointment);
        appointmentService.deleteById(appointment.getId());
        Optional<Appointment> result = appointmentRepository.findById(appointment.getId());
        assertEquals(Optional.empty(),result);
    }
}
