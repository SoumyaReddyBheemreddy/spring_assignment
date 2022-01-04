package com.example.springboot.pet_clinic.dto;

import com.example.springboot.pet_clinic.entity.Pet;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
@Getter@Setter
public class AppointmentDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "date is required")
    private String date;
    @NotNull(message = "description is required")
    private String description;
    @NotNull(message = "amount is required")
    private double amount;
    @NotNull(message = "vet name is required")
    private String vetName;
    @NotNull(message = "vet specialization is required")
    private String specialization;
    private Pet pet;
}
