package com.techelevator.controller;

import com.techelevator.dao.FlightDao;
import com.techelevator.dao.NotificationsDao;
import com.techelevator.dao.TripDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Flight;
import com.techelevator.model.Notification;
import com.techelevator.model.Trip;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path="/trips")
@PreAuthorize("isAuthenticated()")
public class TripController {

    @Autowired
    private TripDao tripDao;

    @Autowired
    private NotificationsDao notificationsDao;

    @Autowired
    private FlightDao flightDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(path="/{name}", method= RequestMethod.POST)
    public Trip createTrip(@PathVariable String name, Principal principal) {

        String username = principal.getName();
        User user = userDao.getUserByUsername(username);

        return tripDao.createTrip(name, user.getId());

    }

    @RequestMapping(path="/{tripId}/flights", method=RequestMethod.GET)
    public List<Flight> getFlights(@PathVariable int tripId){
        return flightDao.getFlights(tripId);
    }

    @RequestMapping(path="/{tripId}/notifications", method=RequestMethod.GET)
    public List<Notification> getNotifications(@PathVariable int tripId){
        return notificationsDao.getNotifications(tripId);
    }

    @RequestMapping(path="/{tripId}", method=RequestMethod.GET)
    public Trip getTrip(@PathVariable int tripId){
        return tripDao.getTrip(tripId);
    }

    @RequestMapping(path="", method=RequestMethod.GET)
    public List<Trip> getTrips(Principal principal){
        String username = principal.getName();
        User user = userDao.getUserByUsername(username);
        return tripDao.getTrips(user.getId());
    }


}
