package com.lambda.foodtruck.models;

public class CurrentLocation
{
    private double longitude;
    private double latitude;

    public CurrentLocation()
    {
    }

    public CurrentLocation(
        double longitude,
        double latitude)
    {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
