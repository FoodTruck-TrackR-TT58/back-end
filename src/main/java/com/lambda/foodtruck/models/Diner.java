package com.lambda.foodtruck.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "diners")
public class Diner
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long dinerid;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String currentlocation;

    @OneToMany(mappedBy = "diner",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnoreProperties(value ="diner",allowSetters = true)
    private Set<Truck> faveTrucks = new HashSet<>();

    public Diner() {
    }

    public Diner(String username, String password, String currentlocation, Set<Truck> faveTrucks) {
        this.username = username;
        this.password = password;
        this.currentlocation = currentlocation;
        this.faveTrucks = faveTrucks;
    }

    public long getDinerid() {
        return dinerid;
    }

    public void setDinerid(long dinerid) {
        this.dinerid = dinerid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCurrentlocation() {
        return currentlocation;
    }

    public void setCurrentlocation(String currentlocation) {
        this.currentlocation = currentlocation;
    }

    public Set<Truck> getFaveTrucks() {
        return faveTrucks;
    }

    public void setFaveTrucks(Set<Truck> faveTrucks) {
        this.faveTrucks = faveTrucks;
    }

    @Override
    public String toString() {
        return "Diner{" +
                "dinerid=" + dinerid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", currentlocation='" + currentlocation + '\'' +
                ", faveTrucks=" + faveTrucks +
                '}';
    }
}
