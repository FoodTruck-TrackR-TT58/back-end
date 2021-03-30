package com.lambda.foodtruck.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "trucks")
public class Truck extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long truckid;


    private String cuisinetype;

    @OneToMany(mappedBy = "truck",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    @JsonIgnoreProperties(value = "truck")
    private List<CustRating> customerratings = new ArrayList<>();

    private int customerratingavg=0;
    private Date departuretime;
    private String location;
    private String imageoftruck;

    @ManyToOne
    @JoinColumn(name = "operatorid",nullable = false)
    @JsonIgnoreProperties(value = "trucksOwned")
    private Operator operator;

    @ManyToOne
    @JoinColumn(name = "dinerid")
    @JsonIgnoreProperties(value = "faveTrucks")
    private Diner diner;

    @OneToMany(mappedBy = "truck",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    @JsonIgnoreProperties(value = "truck",allowSetters = true)
    private List<Menu> menus = new ArrayList<>();

    public Truck()
    {
    }

    public Truck(
        String cuisinetype,
        Operator operator,
        Date departuretime,
        String location,
        String image,
        Diner diner)
    {
        this.cuisinetype = cuisinetype;
        this.operator = operator;
        this.departuretime = departuretime;
        this.location =  location;
        this.imageoftruck = image;
        this.diner = diner;

    }

    public long getTruckid()
    {
        return truckid;
    }

    public void setTruckid(long truckid)
    {
        this.truckid = truckid;
    }

    public String getImageoftruck()
    {
        return imageoftruck;
    }

    public void setImageoftruck(String img)
    {
        this.imageoftruck = img;
    }

    public String getCuisinetype()
    {
        return cuisinetype;
    }

    public void setCuisinetype(String cuisinetype)
    {
        this.cuisinetype = cuisinetype;
    }

    public List<CustRating> getCustomerratings()
    {
        return customerratings;
    }

    public void setCustomerratings(List<CustRating> customerratings)
    {
        this.customerratings = customerratings;
//        for(CustRating cr : customerratings)
//        {
//            this.customerratingavg = this.customerratingavg+cr.getRating();
//        }
//        this.customerratingavg=this.customerratingavg/customerratings.size();
    }

    public int getCustomerratingavg()
    {
        return customerratingavg;
    }

    public void setCustomerratingavg(int customerratingavg)
    {
        this.customerratingavg = customerratingavg;
    }

    public Operator getOperator()
    {
        return operator;
    }

    public void setOperator(Operator operator)
    {
        this.operator = operator;
    }

    public Date getDeparturetime()
    {
        return departuretime;
    }

    public void setDeparturetime(Date departuretime)
    {
        this.departuretime = departuretime;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public List<Menu> getMenus()
    {
        return menus;
    }

    public void setMenus(List<Menu> menus)
    {
        this.menus = menus;
    }

    public Diner getDiner()
    {
        return diner;
    }

    public void setDiner(Diner diner)
    {
        this.diner = diner;
    }
}
