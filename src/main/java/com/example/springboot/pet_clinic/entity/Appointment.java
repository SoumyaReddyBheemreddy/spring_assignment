package com.example.springboot.pet_clinic.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="appointment")
@Getter@Setter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotNull(message = "date is required")
    @Column(name="date")
    private String date;
    @NotNull(message = "description is required")
    @Column(name = "description")
    private String description;

    @Column(name="amount")
    @NotNull(message = "amount is required")
    private double amount;

    @Column(name="vet_name")
    @NotNull(message = "vet name is required")
    private String vetName;
    @Column(name="specialization")
    @NotNull(message = "vet specialization is required")
    private String specialization;
    @ManyToOne(fetch = FetchType.LAZY,cascade ={CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "pet_id")
    private Pet pet;
    public Appointment(){}

    public Appointment(String date, String description, double amount, String vetName, String specialization, Pet pet) {
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.vetName = vetName;
        this.specialization = specialization;
        this.pet = pet;
    }

    @Override
    public String toString() {
        return " Appointment[" +
                "date=" + date +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ']'+"\n";
    }
}
