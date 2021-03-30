package com.lambda.foodtruck.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "custratings")
public class CustRating
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ratingid;

    private int rating;

    @ManyToOne()
    @JoinColumn(name = "truckid",nullable = false)
    @JsonIgnoreProperties(value = "customerratings")
    private Truck truck;

    public CustRating()
    {
    }

    public CustRating(int rating,Truck truck)
    {
        this.rating = rating;
        this.truck = truck;
    }

    public long getRatingid()
    {
        return ratingid;
    }

    public void setRatingid(long ratingid)
    {
        this.ratingid = ratingid;
    }

    public int getRating()
    {
        return rating;
    }

    public void setRating(int rating)
    {
        this.rating = rating;
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
