package com.lambda.foodtruck.services;

import com.lambda.foodtruck.models.Diner;
import com.lambda.foodtruck.repositories.DinerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service(value = "dinerServices")
public class DinerServicesImpl implements DinerServices
{
    @Autowired
    private DinerRepository dinerRepository;

    @Transactional
    @Override
    public Diner save(Diner diner)
    {

        return dinerRepository.save(diner);
    }
}
