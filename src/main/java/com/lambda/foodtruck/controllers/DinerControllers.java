package com.lambda.foodtruck.controllers;

import com.lambda.foodtruck.models.Diner;
import com.lambda.foodtruck.services.DinerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/diners")
public class DinerControllers
{
    @Autowired
    private DinerServices dinerServices;

    @PostMapping(value = "/create/diner", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createDiner(@RequestBody @Valid Diner diner)
    {
        diner.setDinerid(0);
        dinerServices.save(diner);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{dinerid}")
                .buildAndExpand(diner.getDinerid())
                .toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }
}
