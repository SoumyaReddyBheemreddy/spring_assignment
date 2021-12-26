package com.example.springboot.pet_clinic.entity;

import lombok.Getter;
import lombok.Setter;



import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="pet")
@Getter@Setter
public class Pet {
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
    @OneToMany(fetch = FetchType.LAZY,cascade =  CascadeType.ALL,mappedBy = "pet")
    private Set<Appointment> appointments;
    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.MERGE,CascadeType.REFRESH})
    @JoinTable(
            name = "owner_pet",
            joinColumns = @JoinColumn(name="pet_id"),
            inverseJoinColumns = @JoinColumn(name="owner_id")
    )
    private Set<Owner> owners;
    public Pet(){}

    public Pet(String name, String type, Set<Appointment> appointments, Set<Owner> owners) {
        this.name = name;
        this.type = type;
        this.appointments = appointments;
        this.owners = owners;
    }

    @Override
    public String toString() {
        return " Pet[" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ']'+"\n";
    }
    public void addAppointment(Appointment appointment){
        if(appointments==null)
            appointments = new HashSet<>();
        appointments.add(appointment);
    }
    public void addOwner(Owner owner){
        if(owners==null)
            owners = new HashSet<>();
        owners.add(owner);
    }

}
