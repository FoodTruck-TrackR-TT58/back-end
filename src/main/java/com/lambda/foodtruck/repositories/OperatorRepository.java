package com.lambda.foodtruck.repositories;

import com.lambda.foodtruck.models.Operator;
import org.springframework.data.repository.CrudRepository;

public interface OperatorRepository extends CrudRepository<Operator,Long>
{
    Operator findByUsername(String name);
}
