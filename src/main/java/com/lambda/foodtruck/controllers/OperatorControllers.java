package com.lambda.foodtruck.controllers;

import com.lambda.foodtruck.models.Operator;
import com.lambda.foodtruck.repositories.OperatorRepository;
import com.lambda.foodtruck.services.OperatorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/operators")
public class OperatorControllers
{
    @Autowired
    private OperatorServices operatorServices;

    @GetMapping(value = "/operators",produces = "application/json")
    public ResponseEntity<?> findAllOperators()
    {
        List<Operator> operators = operatorServices.findAllOperators();
        return new ResponseEntity<>(operators,HttpStatus.OK);
    }

    @GetMapping(value = "/operator/{operid}",produces = "application/json")
    public ResponseEntity<?> findOperatorByid(@PathVariable long operid)
    {
        Operator operator = operatorServices.findOperatorByid(operid);
        return new ResponseEntity<>(operator,HttpStatus.OK);
    }

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

    @PutMapping(value = "/replace/operator/{operid}",consumes = "application/json",produces = "application/json")
    public ResponseEntity<?> replaceOperator(@PathVariable long operid, @RequestBody @Valid Operator operator)
    {
        operator.setUserid(operid);
        operatorServices.save(operator);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value= "/update/operator/{operid}",consumes = "application/json",produces = "application/json")
    public ResponseEntity<?> updateOperator(@PathVariable long operid,@RequestBody Operator operator)
    {
        operatorServices.update(operid,operator);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
