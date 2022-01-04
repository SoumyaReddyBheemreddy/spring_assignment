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
    @Column(name = "id")
    private int id;
    @NotNull(message = "pet name is required")
    @Column(name = "name")
    private String name;
    @NotNull(message = "pet type is required")
    @Column(name = "type")
    private String type;

    private Set<Owner> owners;

    private Set<Appointment> appointments;
}
