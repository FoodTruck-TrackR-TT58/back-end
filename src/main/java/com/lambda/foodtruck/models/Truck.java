package com.lambda.foodtruck.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "trucks")
public class Truck
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

    private int customerratingavg;
    private Date departuretime;
    private String location;
    private ImageIcon imageoftruck;

    @ManyToOne
    @JoinColumn(name = "operatorid",nullable = false)
    @JsonIgnoreProperties(value = "trucksOwned")
    private Operator operator;

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
        String location)
    {
        this.cuisinetype = cuisinetype;
        this.operator = operator;
        this.departuretime = departuretime;
        this.location =  location;
    }

    public long getTruckid()
    {
        return truckid;
    }

    public void setTruckid(long truckid)
    {
        this.truckid = truckid;
    }

    public ImageIcon getImageoftruck()
    {
        return imageoftruck;
    }

    public void setImageoftruck(String imageoftruck,Image image)
    {
        if(imageoftruck != null)
    {
        this.imageoftruck = new ImageIcon(imageoftruck);
    }
    else if(image!=null)
    {
        this.imageoftruck = new ImageIcon(image);
    }
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
        for(CustRating cr : customerratings)
        {
            this.customerratingavg = this.customerratingavg+cr.getRating();
        }
        this.customerratingavg=this.customerratingavg/customerratings.size();
    }

    public int getCustomerratingavg()
    {
        return customerratingavg;
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
}
