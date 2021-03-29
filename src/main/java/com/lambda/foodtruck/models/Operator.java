package com.lambda.foodtruck.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "operators")
public class Operator
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userid;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;


    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @OneToMany(mappedBy = "operator",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    @JsonIgnoreProperties(value ="operator",allowSetters = true)
    private Set<Truck> trucksOwned = new HashSet<>();

    public Operator()
    {
    }

    public Operator(
        String username,
        String password,
        @Email String email)
    {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public long getUserid()
    {
        return userid;
    }

    public void setUserid(long userid)
    {
        this.userid = userid;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Set<Truck> getTrucksOwned()
    {
        return trucksOwned;
    }

    public void setTrucksOwned(Set<Truck> trucksOwned)
    {
        this.trucksOwned = trucksOwned;
    }
}
