package com.example.springboot.pet_clinic.service;

import com.example.springboot.pet_clinic.dao.AppointmentRepository;
import com.example.springboot.pet_clinic.entity.Appointment;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Log4j2
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }
    @Override
    public Appointment findById(int id) {
        Optional<Appointment> result = appointmentRepository.findById(id);
        Appointment appointment = null;
        if(result.isPresent()){
            appointment = result.get();
        }
        else {
            log.error("Invalid Appointment id - "+id);
            throw new RuntimeException("Appointment id is not found - " + id);
        }
        return appointment;
    }

    @Override
    public void save(Appointment owner) {
        appointmentRepository.save(owner);
    }

    @Override
    public void deleteById(int id) {
        appointmentRepository.deleteById(id);
    }
}
