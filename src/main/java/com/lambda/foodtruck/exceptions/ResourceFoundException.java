package com.lambda.foodtruck.exceptions;

public class ResourceFoundException extends RuntimeException
{
    public ResourceFoundException(String message)
    {
        super("Found an issue with School: " + message);
    }
}