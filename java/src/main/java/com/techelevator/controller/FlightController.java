package com.techelevator.controller;

import com.techelevator.dao.FlightDao;
import com.techelevator.model.Flight;
import com.techelevator.services.AviationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path="/flights/")
public class FlightController {

    @Autowired
    private FlightDao flightDao;

    @Autowired
    private AviationService aviationService;

    @RequestMapping(path="/{id}", method= RequestMethod.GET)
    public Flight getFlight(@PathVariable int id) {
        return flightDao.getFlight(id);
    }

    @RequestMapping(path="/{tripId}", method=RequestMethod.POST)
    public Flight createFlight(@RequestBody Flight flight, @PathVariable int tripId) {
        return flightDao.createFlight(flight, tripId);
    }

    @RequestMapping(path="status/{flightNumber}", method=RequestMethod.GET)
    public String getFlightStatus(@PathVariable String flightNumber){
        return aviationService.getFlightStatus(flightNumber);
    }

}
