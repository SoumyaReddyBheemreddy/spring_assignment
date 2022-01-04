package com.example.springboot.pet_clinic.converter;

import com.example.springboot.pet_clinic.dto.AppointmentDTO;
import com.example.springboot.pet_clinic.entity.Appointment;
import org.modelmapper.ModelMapper;

public class AppointmentConverter {
    public AppointmentDTO entityToDto(Appointment appointment){
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setId(appointment.getId());
        appointmentDTO.setDate(appointment.getDate());
        appointmentDTO.setAmount(appointment.getAmount());
        appointmentDTO.setDescription(appointment.getDescription());
        appointmentDTO.setVetName(appointment.getVetName());
        appointmentDTO.setSpecialization(appointment.getSpecialization());
        appointmentDTO.setPet(appointment.getPet());
        return appointmentDTO;
    }
    public Appointment dtoToEntity(AppointmentDTO appointmentDTO){
        ModelMapper mapper=new ModelMapper();
        return mapper.map(appointmentDTO, Appointment.class);
    }
}
