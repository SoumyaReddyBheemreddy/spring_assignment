package com.example.springboot.pet_clinic.dto;

import com.example.springboot.pet_clinic.entity.Pet;
import com.example.springboot.pet_clinic.entity.Users;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;
@Getter@Setter
public class OwnerDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "Owner name is required")
    private String name;
    @Pattern(regexp = "^[6-9]\\d{9}$",message ="enter 10 digit phone number")
    @NotNull(message = "phone number required")
    private String phoneNumber;
    private Users users;
    private Set<Pet> pets;
}
