package com.lambda.foodtruck.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "diners")
public class Diner extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long dinerid;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Email
    @Column(unique = true,nullable = false)
    private String email;

    private String currentlocation;

    @OneToMany(mappedBy = "diner",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnoreProperties(value ="diner",allowSetters = true)
    private Set<Truck> faveTrucks = new HashSet<>();

    public Diner() {
    }

    public Diner(String username, String password,String email, String currentlocation) {
        setUsername(username);
        setPassword(password);
        this.email = email;
        this.currentlocation = currentlocation;
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
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }

    public void setPasswordNoEncrypt(String password)
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

    @JsonIgnore
    public List<SimpleGrantedAuthority> getAuthority()
    {
        List<SimpleGrantedAuthority> rtnList = new ArrayList<>();

        String myRole = "ROLE_" + "DINER";
        rtnList.add(new SimpleGrantedAuthority(myRole));

        return rtnList;
    }
}
