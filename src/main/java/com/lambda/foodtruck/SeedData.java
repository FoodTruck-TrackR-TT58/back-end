package com.lambda.foodtruck;

import com.lambda.foodtruck.models.Operator;
import com.lambda.foodtruck.models.Truck;

import com.lambda.foodtruck.repositories.TruckRepository;
import com.lambda.foodtruck.services.OperatorServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import java.util.Date;

@Transactional
@ConditionalOnProperty(
    prefix = "command.line.runner",
    value = "enabled",
    havingValue = "true",
    matchIfMissing = true)
@Component
public class SeedData
    implements CommandLineRunner
{
    @Autowired
    private OperatorServices operatorServices;

    @Autowired
    private TruckRepository truckRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception
    {
        Operator o1 = new Operator("fidan", "password","fn@fgn.jk");
        o1.getTrucksOwned().add(new Truck( "italian",
            o1,
             new Date(),
            "main street",
            "image"));

        operatorServices.save(o1);
    }
}
