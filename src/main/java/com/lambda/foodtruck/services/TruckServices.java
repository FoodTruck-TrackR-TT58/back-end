package com.lambda.foodtruck.services;

import com.lambda.foodtruck.models.Truck;

import java.util.List;

public interface TruckServices
{
    List<Truck> findAllTrucks();
    Truck findTruckByid(long id);
    Truck update(Truck truck,long id);
    Truck save(Truck truck);
}
