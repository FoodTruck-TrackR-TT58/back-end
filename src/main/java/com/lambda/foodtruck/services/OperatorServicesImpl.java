package com.lambda.foodtruck.services;

import com.lambda.foodtruck.exceptions.ResourceNotFoundException;
import com.lambda.foodtruck.models.CustRating;
import com.lambda.foodtruck.models.Menu;
import com.lambda.foodtruck.models.Operator;
import com.lambda.foodtruck.models.Truck;
import com.lambda.foodtruck.repositories.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Transactional
@Service(value = "operatorServices")
public class OperatorServicesImpl implements OperatorServices
{
   @Autowired
   private OperatorRepository operatorRepository;

   @Autowired
   private  HelperFunctions helperFunctions;

    @Override
    public List<Operator> findAllOperators()
    {
        List<Operator> operators = new ArrayList<>();
        operatorRepository.findAll().iterator().forEachRemaining(operators::add);
        return operators;
    }

    @Override
    public Operator findOperatorByid(long id)
    {
        Operator operator = operatorRepository.findById(id)
            .orElseThrow(()->new ResourceNotFoundException("Operator " + id +" not found"));

        return operator;
    }

    @Transactional
    @Override
    public Operator save(Operator operator)
    {
        Operator newoperator = new Operator();

        if(operator.getUserid()!=0)
        {
           Operator operator1 = operatorRepository.findById(operator.getUserid())
                .orElseThrow(()-> new ResourceNotFoundException("Operator "+operator.getUserid()+"not found"));
            if(helperFunctions.isAuthorizedToMakeChange(operator1.getUsername()))
            {
                newoperator.setUserid(operator.getUserid());
            }
        }

        newoperator.setUsername(operator.getUsername());
        newoperator.setEmail(operator.getEmail());
        newoperator.setPasswordNoEncrypt(operator.getPassword());

        newoperator.getTrucksOwned().clear();
        for(Truck tr: operator.getTrucksOwned())
        {
          Truck truck = new Truck(
              tr.getCuisinetype(),
              newoperator,
              tr.getDeparturetime(),
              tr.getLocation(),
              tr.getImageoftruck(),
              tr.getDiner());


          for(CustRating cr: tr.getCustomerratings())
          {
              CustRating custRating = new CustRating(cr.getRating(),truck);
              truck.getCustomerratings().add(custRating);
          }

          for(Menu m: tr.getMenus())
          {
              truck.getMenus().add(new Menu(
                  m.getItemname(),
                  m.getItemdescription(),
                  m.getItemprice(),
                  truck));
          }
          newoperator.getTrucksOwned().add(truck);
        }
        return operatorRepository.save(newoperator);
    }

    @Override
    public Operator update(
        long id,
        Operator operator)
    {
            Operator newoperator = operatorRepository.findById(operator.getUserid())
                    .orElseThrow(()-> new ResourceNotFoundException("Operator "+operator.getUserid()+"not found"));

        if(helperFunctions.isAuthorizedToMakeChange(newoperator.getUsername()))
        {
            newoperator.setUsername(operator.getUsername());
            newoperator.setEmail(operator.getEmail());
            newoperator.setPasswordNoEncrypt(operator.getPassword());

            newoperator.getTrucksOwned().clear();
            for(Truck tr: operator.getTrucksOwned())
            {
                Truck truck = new Truck(
                    tr.getCuisinetype(),
                    newoperator,
                    tr.getDeparturetime(),
                    tr.getLocation(),
                    tr.getImageoftruck(),
                    tr.getDiner());


                for(CustRating cr: tr.getCustomerratings())
                {
                    CustRating custRating = new CustRating(cr.getRating(),truck);
                    truck.getCustomerratings().add(custRating);
                }

                for(Menu m: tr.getMenus())
                {
                    truck.getMenus().add(new Menu(
                        m.getItemname(),
                        m.getItemdescription(),
                        m.getItemprice(),
                        truck));
                }
                newoperator.getTrucksOwned().add(truck);
            }
            return operatorRepository.save(newoperator);
        }
        else
        {
            throw new ResourceNotFoundException("This user is not authorized to make changes");
        }
    }

    @Override
    public Operator findByName(String name)
    {
        Operator operator = operatorRepository.findByUsername(name.toLowerCase());
        if(operator == null)
        {
            throw new ResourceNotFoundException("Operator not found");
        }
        return operator;
    }
}
