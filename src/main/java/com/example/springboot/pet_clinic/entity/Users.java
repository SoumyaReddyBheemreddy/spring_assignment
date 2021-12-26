package com.example.springboot.pet_clinic.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter @Setter
public class Users {
    @Id
    @Column(name="username")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "enabled")
    private int enabled;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @OneToMany(fetch = FetchType.LAZY,cascade =  CascadeType.ALL,mappedBy = "user")
    private Set<Authorities> authorities;
    public Users(){}
    public Users(String userName, String password, int enabled, Owner owner) {
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
        this.owner = owner;
    }
    @Override
    public String toString() {
        return "Users{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", owner=" + owner +
                '}';
    }
    public void addAuthorities(Authorities authoritie){
        if(authorities==null)
            authorities = new HashSet<>();
        authorities.add(authoritie);

    }
}
