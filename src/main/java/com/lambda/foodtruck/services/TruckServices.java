package com.lambda.foodtruck.services;

import com.lambda.foodtruck.models.Truck;

import java.util.List;

public interface TruckServices
{
    List<Truck> findAllTrucks();
    Truck save(Truck truck);
}
