package com.lambda.foodtruck.services;

import com.lambda.foodtruck.models.Diner;
import com.lambda.foodtruck.models.Truck;
import com.lambda.foodtruck.repositories.DinerRepository;
import com.lambda.foodtruck.repositories.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Transactional
@Service(value = "dinerServices")
public class DinerServicesImpl implements DinerServices
{
    @Autowired
    private DinerRepository dinerRepository;

    @Autowired
    private TruckServices truckServices;

    @Transactional
    @Override
    public Diner save(Diner diner)
    {
        Diner newDiner = new Diner();

        if(diner.getDinerid() != 0)
        {
            dinerRepository.findById(diner.getDinerid())
                .orElseThrow(()->new EntityNotFoundException("Diner "+diner.getDinerid()+" not found"));
            newDiner.setDinerid(diner.getDinerid());
        }

        newDiner.setUsername(diner.getUsername());
        newDiner.setEmail(diner.getEmail());
        newDiner.setPasswordNoEncrypt(diner.getPassword());
        newDiner.setCurrentlocation(diner.getCurrentlocation());

        newDiner.getFaveTrucks().clear();
        for(Truck tr: diner.getFaveTrucks())
        {
            Truck truck = truckServices.findTruckByid(tr.getTruckid());
            newDiner.getFaveTrucks().add(truck);
        }

        return dinerRepository.save(newDiner);
    }
}
