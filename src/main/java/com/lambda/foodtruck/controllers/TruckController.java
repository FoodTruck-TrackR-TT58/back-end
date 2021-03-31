package com.lambda.foodtruck.controllers;

import com.lambda.foodtruck.models.Operator;
import com.lambda.foodtruck.models.Truck;
import com.lambda.foodtruck.services.OperatorServices;
import com.lambda.foodtruck.services.TruckServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/trucks")
public class TruckController
{

    @Autowired
    private TruckServices truckServices;

    @Autowired
    private OperatorServices operatorServices;
    @GetMapping(value = "/trucks",produces = "application/json")
    public ResponseEntity<?> findAlltrucks()
    {
        List<Truck> trucks = truckServices.findAllTrucks();
        return new ResponseEntity<>(trucks,
            HttpStatus.OK);
    }

//    @GetMapping(value = "/mytrucks",produces = "application/json")
//    public ResponseEntity<?> findMyTrucks(){
//        Authentication authentication = SecurityContextHolder.getContext()
//            .getAuthentication();
//    }

    @GetMapping(value = "/truck/{truckid}",produces = "application/json")
    public ResponseEntity<?> findTruckByid(@PathVariable long truckid)
    {
        Truck truck = truckServices.findTruckByid(truckid);
        return new ResponseEntity<>(truck,HttpStatus.OK);
    }

    @PostMapping(value ="/truck",consumes = "application/json",produces = "application/json")
    public ResponseEntity<?> addNewTruck(@RequestBody @Valid Truck truck)
    {
        truck.setTruckid(0);
        truck = truckServices.save(truck);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newTruckURI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{truckid}")
            .buildAndExpand(truck.getTruckid())
            .toUri();
        responseHeaders.setLocation(newTruckURI);

        return new ResponseEntity<>(null,responseHeaders,HttpStatus.CREATED);
    }

    @PutMapping(value = "/truck/{truckid}",consumes = "application/json",produces = "application/json")
    public ResponseEntity replaceTruck(@PathVariable long truckid,@RequestBody Truck truck)
    {
        truck.setTruckid(truckid);

        truckServices.save(truck);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/truck/{truckid}",produces = "application/json",consumes = "application/json")
    public ResponseEntity<?> updateTruck(@PathVariable long truckid,@RequestBody Truck truck)
    {
      truck.setTruckid(truckid);
      truckServices.update(truck,truckid);
      return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/truck/{truckid}")
    public ResponseEntity<?> deleteTruck(@PathVariable long truckid){
        truckServices.deleteTruckByid(truckid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
