package com.lambda.foodtruck.services;

import com.lambda.foodtruck.models.Diner;
import com.lambda.foodtruck.models.Operator;
import com.lambda.foodtruck.repositories.DinerRepository;
import com.lambda.foodtruck.repositories.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Locale;

@Service(value = "securityUserService")
public class SecurityUserServiceImpl implements UserDetailsService
{
    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private DinerRepository dinerRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException
    {

        Operator operator = operatorRepository.findByUsername(s.toLowerCase());

        Diner diner = dinerRepository.findDinerByUsername(s.toLowerCase());
        if(operator!=null)
        {
            return new org.springframework.security.core.userdetails.User(operator.getUsername(),
                operator.getPassword(),
                operator.getAuthority());
        }
        else if(diner!=null)
        {
            return new org.springframework.security.core.userdetails.User(diner.getUsername(),
                diner.getPassword(),
                diner.getAuthority());
        }
        else
        {
            throw new EntityNotFoundException("Invalid username or password.");
        }
    }
}
