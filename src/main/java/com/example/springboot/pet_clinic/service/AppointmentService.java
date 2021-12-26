package com.example.springboot.pet_clinic.service;

import com.example.springboot.pet_clinic.entity.Appointment;

import java.util.List;

public interface AppointmentService {
    public List<Appointment> findAll();
    public Appointment findById(int id);
    public void save(Appointment owner);
    public void deleteById(int id);
}
