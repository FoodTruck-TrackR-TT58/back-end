package com.lambda.foodtruck.repositories;

import com.lambda.foodtruck.models.Truck;
import org.springframework.data.repository.CrudRepository;

public interface TruckRepository extends CrudRepository<Truck,Long>
{
}
