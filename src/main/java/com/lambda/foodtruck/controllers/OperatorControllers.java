package com.lambda.foodtruck.controllers;

import com.lambda.foodtruck.models.Operator;
import com.lambda.foodtruck.repositories.OperatorRepository;
import com.lambda.foodtruck.services.OperatorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class OperatorControllers
{
    @Autowired
    private OperatorServices operatorServices;

    @PostMapping(value = "/create/operator",consumes = "application/json",produces = "application/json")
    public ResponseEntity<?> createOperator(@RequestBody @Valid Operator operator)
    {
        operator.setUserid(0);
        operatorServices.save(operator);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{operid}")
            .buildAndExpand(operator.getUserid())
            .toUri();
        responseHeaders.setLocation(newUserURI);

        return  new ResponseEntity<>(null,responseHeaders,
            HttpStatus.CREATED);
    }
}
