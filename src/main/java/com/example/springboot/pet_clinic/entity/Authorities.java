package com.example.springboot.pet_clinic.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
@Getter@Setter
public class Authorities {
    @Id
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "username")
    private Users user;
    @Column(name = "authority")
    private String role;
    public Authorities(){}

    public Authorities(int id,Users user, String role) {
        this.id = id;
        this.user = user;
        this.role = role;
    }

    @Override
    public String toString() {
        return "Authorities{" +
                "id=" + id +
                ", user=" + user +
                ", role='" + role + '\'' +
                '}';
    }
}
