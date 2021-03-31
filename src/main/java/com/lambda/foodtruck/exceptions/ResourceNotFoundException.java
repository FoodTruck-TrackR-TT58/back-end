package com.lambda.foodtruck.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super("Error from a lambda school app: " + message);
    }
}