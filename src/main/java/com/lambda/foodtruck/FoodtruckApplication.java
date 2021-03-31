package com.lambda.foodtruck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@PropertySource(value = "file:/Users/yf/foodtruck.properties",ignoreResourceNotFound = true)
public class FoodtruckApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(FoodtruckApplication.class,
            args);
    }

}
