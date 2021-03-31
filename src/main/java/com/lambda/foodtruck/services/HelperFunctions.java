package com.lambda.foodtruck.services;

import com.lambda.foodtruck.models.ValidationError;

import java.util.List;

public interface HelperFunctions
{
    List<ValidationError> getConstraintViolation(Throwable cause);
}
