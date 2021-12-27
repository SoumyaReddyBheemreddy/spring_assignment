package com.example.springboot.pet_clinic.entity;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;

import java.util.Set;

@Entity
@Table(name = "owner")
@Getter@Setter
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotNull(message = "Owner name is required")
    @Column(name="name")
    private String name;
    @Pattern(regexp = "^[6-9]\\d{9}$",message ="enter 10 digit phone number")
    @NotNull(message = "phone number required")
    @Column(name="phone_number")
    private String phoneNumber;
    @OneToOne(fetch = FetchType.LAZY,mappedBy = "owner",cascade = CascadeType.ALL)
    private Users users;
    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.MERGE,CascadeType.REFRESH})
    @JoinTable(
            name = "owner_pet",
            joinColumns = @JoinColumn(name="owner_id"),
            inverseJoinColumns = @JoinColumn(name="pet_id")
    )
    private Set<Pet> pets;
    public Owner(){}

    public Owner(String name, String phoneNumber, Users users, Set<Pet> pets) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.users = users;
        this.pets = pets;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", users=" + users +
                ", pets=" + pets +
                '}';
    }

    public void addPet(Pet pet){
        if(pets==null)
            pets = new HashSet<>();
        pets.add(pet);
    }

}
