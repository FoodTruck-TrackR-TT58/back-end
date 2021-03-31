package com.lambda.foodtruck;

import com.lambda.foodtruck.models.Diner;
import com.lambda.foodtruck.models.Menu;
import com.lambda.foodtruck.models.Operator;
import com.lambda.foodtruck.models.Truck;

import com.lambda.foodtruck.repositories.DinerRepository;
import com.lambda.foodtruck.repositories.TruckRepository;
import com.lambda.foodtruck.services.DinerServices;
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
    private DinerServices dinerServices;

    @Transactional
    @Override
    public void run(String... args) throws Exception
    {
        Operator o1 = new Operator("coolrunner", "lambdallama", "coolrunner12@gmail.com");
        Diner d1 = new Diner("Ramasundar", "lambdallama","lambda@mail.lm", "47.7066144,-116.8551");
        d1 = dinerServices.save(d1);

        Truck t1 = new Truck( "American", o1,new Date() ,"47.7066144,-116.8551", "",d1);
        t1.getMenus().add(new Menu("taco", "Meat, cheese, and vegetables in a tortilla", 4.35, t1));
        o1.getTrucksOwned().add(t1);

        Truck t2 = new Truck( "Asian", o1,new Date() ,"47.7066144,-116.8551", "",d1);
        t2.getMenus().add(new Menu("sushi", "fish,avocado,rice", 6.35, t2));
        o1.getTrucksOwned().add(t2);


        operatorServices.save(o1);


    }
}
