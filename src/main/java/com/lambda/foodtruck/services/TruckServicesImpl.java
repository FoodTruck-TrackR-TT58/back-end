package com.lambda.foodtruck.services;

import com.lambda.foodtruck.models.CustRating;
import com.lambda.foodtruck.models.Menu;
import com.lambda.foodtruck.models.Operator;
import com.lambda.foodtruck.models.Truck;
import com.lambda.foodtruck.repositories.OperatorRepository;
import com.lambda.foodtruck.repositories.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "truckServices")
public class TruckServicesImpl implements TruckServices
{
    @Autowired
    private TruckRepository truckRepository;

    @Autowired
    private OperatorRepository operatorRepository;
    @Override
    public List<Truck> findAllTrucks()
    {
        List<Truck> trucks = new ArrayList<>();
            truckRepository.findAll().iterator().forEachRemaining(trucks::add);
        return trucks;
    }

    @Transactional
    @Override
    public Truck save(Truck truck)
    {
        Truck newTruck = new Truck();

        if(truck.getTruckid()!= 0)
        {
            truckRepository.findById(truck.getTruckid())
                .orElseThrow(()-> new EntityNotFoundException("Truck "+ truck.getTruckid()+" not found"));
            newTruck.setTruckid(truck.getTruckid());
        }

        newTruck.setCuisinetype(truck.getCuisinetype());
        newTruck.setDeparturetime(truck.getDeparturetime());
        newTruck.setLocation(truck.getLocation());
        Operator operator = operatorRepository.findById(truck.getOperator().getUserid())
            .orElseThrow(()->new EntityNotFoundException("Operator not found"+ truck.getOperator().getUserid()));
        newTruck.setOperator(operator);

        newTruck.getMenus().clear();
        for(Menu m: truck.getMenus())
        {
            newTruck.getMenus()
                .add(new Menu(m.getItemname(),m.getItemdescription(),m.getItemprice(),newTruck));
        }

        newTruck.getCustomerratings().clear();
        for(CustRating cr : truck.getCustomerratings())
        {
            newTruck.getCustomerratings()
                .add(new CustRating(cr.getRating(),newTruck));
        }

        newTruck.setImageoftruck(truck.getImageoftruck());
        return truckRepository.save(newTruck);
    }
}
