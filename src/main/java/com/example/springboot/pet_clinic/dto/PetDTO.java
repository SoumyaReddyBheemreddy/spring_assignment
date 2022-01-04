package com.example.springboot.pet_clinic.dto;

import com.example.springboot.pet_clinic.entity.Appointment;
import com.example.springboot.pet_clinic.entity.Owner;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;
@Getter
@Setter
public class PetDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    @NotNull(message = "pet type is required")

    private String type;

    @NotNull(message = "pet name is required")

    private String name;

    private Set<Owner> owners;

    private Set<Appointment> appointments;
}
