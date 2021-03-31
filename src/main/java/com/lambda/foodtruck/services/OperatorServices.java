package com.lambda.foodtruck.services;

import com.lambda.foodtruck.models.Operator;

import java.util.List;

public interface OperatorServices
{
    Operator save(Operator operator);
    List<Operator> findAllOperators();
    Operator findOperatorByid(long id);
    Operator findByName(String name);
}
