package com.lambda.foodtruck.repositories;

import com.lambda.foodtruck.models.Diner;
import org.springframework.data.repository.CrudRepository;

public interface DinerRepository extends CrudRepository<Diner, Long>
{
    Diner findDinerByUsername(String username);
}
