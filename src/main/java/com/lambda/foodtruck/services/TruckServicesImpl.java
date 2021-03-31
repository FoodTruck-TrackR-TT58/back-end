package com.lambda.foodtruck.services;

import com.lambda.foodtruck.exceptions.ResourceNotFoundException;
import com.lambda.foodtruck.models.CustRating;
import com.lambda.foodtruck.models.Menu;
import com.lambda.foodtruck.models.Operator;
import com.lambda.foodtruck.models.Truck;
import com.lambda.foodtruck.repositories.OperatorRepository;
import com.lambda.foodtruck.repositories.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "truckServices")
public class TruckServicesImpl implements TruckServices
{
    @Autowired
    private TruckRepository truckRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private HelperFunctions helperFunctions;

    @Override
    public List<Truck> findAllTrucks()
    {
        List<Truck> trucks = new ArrayList<>();
            truckRepository.findAll().iterator().forEachRemaining(trucks::add);
        return trucks;
    }

    @Override
    public Truck findTruckByid(long id)
    {
        Truck truck = truckRepository.findById(id)
            .orElseThrow(()->new ResourceNotFoundException("Truck "+id +" not found"));
        return truck;
    }

    @Transactional
    @Override
    public Truck save(Truck truck)
    {
        Truck newTruck = new Truck();

        Authentication authentication = SecurityContextHolder.getContext()
            .getAuthentication();

        if(truck.getTruckid()!= 0 )
        {
          Truck truck1 = truckRepository.findById(truck.getTruckid())
                .orElseThrow(()-> new ResourceNotFoundException("Truck "+ truck.getTruckid()+" not found"));
          if(helperFunctions.isAuthorizedToMakeChange(truck1.getOperator().getUsername()))
          {
              newTruck.setTruckid(truck.getTruckid());
          }
        }

        newTruck.setCuisinetype(truck.getCuisinetype());
        newTruck.setDeparturetime(truck.getDeparturetime());
        newTruck.setLocation(truck.getLocation());
        Operator operator = operatorRepository.findByUsername(authentication.getName());
        newTruck.setOperator(operator);

        newTruck.getMenus().clear();
        for(Menu m: truck.getMenus())
        {
            newTruck.getMenus()
                .add(new Menu(m.getItemname(),m.getItemdescription(),m.getItemprice(),newTruck));
        }

        newTruck.getCustomerratings().clear();
        int avg = 0;
        for(CustRating cr : truck.getCustomerratings())
        {
            avg = cr.getRating() + avg;
            newTruck.getCustomerratings()
                .add(new CustRating(cr.getRating(),newTruck));

        }
        if(truck.getCustomerratings().size()>0)
        {
            newTruck.setCustomerratingavg(avg / truck.getCustomerratings()
                .size());
        }

        newTruck.setImageoftruck(truck.getImageoftruck());
        return truckRepository.save(newTruck);
    }

    @Override
    public Truck update(
        Truck truck,
        long id)
    {
         if(helperFunctions.isAuthorizedToMakeChange(truck.getOperator().getUsername()))
         {
             Truck updTruck = truckRepository.findById(truck.getTruckid())
                 .orElseThrow(() -> new ResourceNotFoundException("Truck " + truck.getTruckid() + " not found"));

             if (truck.getCuisinetype() != null)
             {
                 updTruck.setCuisinetype(truck.getCuisinetype());
             }
             if (truck.getDeparturetime() != null)
             {
                 updTruck.setDeparturetime(truck.getDeparturetime());
             }
             if (truck.getLocation() != null)
             {
                 updTruck.setLocation(truck.getLocation());
             }
             if (truck.getOperator() != null)
             {
                 Operator operator = operatorRepository.findById(truck.getOperator()
                     .getUserid())
                     .orElseThrow(() -> new ResourceNotFoundException("Operator not found" + truck.getOperator()
                         .getUserid()));
                 updTruck.setOperator(operator);
             }

             if (truck.getMenus()
                 .size() > 0)
             {
                 updTruck.getMenus()
                     .clear();
                 for (Menu m : truck.getMenus())
                 {
                     updTruck.getMenus()
                         .add(new Menu(m.getItemname(),
                             m.getItemdescription(),
                             m.getItemprice(),
                             updTruck));
                 }
             }

             if (truck.getCustomerratings()
                 .size() > 0)
             {
                 updTruck.getCustomerratings()
                     .clear();
                 int avg = 0;
                 for (CustRating cr : truck.getCustomerratings())
                 {
                     avg = cr.getRating() + avg;
                     updTruck.getCustomerratings()
                         .add(new CustRating(cr.getRating(),
                             updTruck));

                 }
                 if (truck.getCustomerratings()
                     .size() > 0)
                 {
                     updTruck.setCustomerratingavg(avg / truck.getCustomerratings()
                         .size());
                 }
             }

             if (truck.getImageoftruck() != null)
             {
                 updTruck.setImageoftruck(truck.getImageoftruck());
             }
             return truckRepository.save(updTruck);
         }
         else
         {
             throw new ResourceNotFoundException("This user is not authorized to make changes");
         }
    }

    @Transactional
    @Override
    public void deleteTruckByid(long id)
    {
        Truck truck = truckRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Truck "+id+" not found"));
        if(helperFunctions.isAuthorizedToMakeChange(truck.getOperator().getUsername()))
        {
            truckRepository.deleteById(id);
        }
    }
}
