package com.lambda.foodtruck.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menus")
public class Menu
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long menuid;

    private String itemname;
    private String itemdescription;

    @OneToMany(mappedBy = "menu",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    @JsonIgnoreProperties(value = "menu",allowSetters = true)
    private List<Images> itemphotos = new ArrayList<>();

    private double itemprice;
    //private List<Double> custRatings = new ArrayList<>();
    private double custratingavg;

    @ManyToOne
    @JoinColumn(name = "truckid",
    nullable = false)
    @JsonIgnoreProperties(value = "menus",
    allowSetters = true)
     private Truck truck;

    public Menu()
    {
    }

    public Menu(
        String itemname,
        String itemdescription,
        double itemprice,
        Truck truck
    )
    {
        this.itemname = itemname;
        this.itemdescription = itemdescription;
        this.itemprice = itemprice;
        this.truck = truck;
    }

    public long getMenuid()
    {
        return menuid;
    }

    public void setMenuid(long menuid)
    {
        this.menuid = menuid;
    }

    public String getItemname()
    {
        return itemname;
    }

    public void setItemname(String itemname)
    {
        this.itemname = itemname;
    }

    public String getItemdescription()
    {
        return itemdescription;
    }

    public void setItemdescription(String itemdescription)
    {
        this.itemdescription = itemdescription;
    }

    public List<Images> getItemphotos()
    {
        return itemphotos;
    }

    public void setItemphotos(List<Images> itemphotos)
    {
        this.itemphotos = itemphotos;
    }

    public double getItemprice()
    {
        return itemprice;
    }

    public void setItemprice(double itemprice)
    {
        this.itemprice = itemprice;
    }

//    public List<Double> getCustRatings()
//    {
//        return custRatings;
//    }
//
//    public void setCustRatings(Double custRatings)
//    {
//        this.custRatings.add(custRatings);
//        double total = 0;
//        for(double cr : this.custRatings)
//        {
//            total = total+cr;
//        }
//        this.custratingavg = total/this.custRatings.size();
//    }

    public double getCustratingavg()
    {
        return custratingavg;
    }

    public Truck getTruck()
    {
        return truck;
    }

    public void setTruck(Truck truck)
    {
        this.truck = truck;
    }
}
