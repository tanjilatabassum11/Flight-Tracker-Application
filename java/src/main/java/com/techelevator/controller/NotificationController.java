package com.techelevator.controller;

import com.techelevator.dao.FlightDao;
import com.techelevator.dao.NotificationsDao;
import com.techelevator.model.Flight;
import com.techelevator.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path="/notifications/")
public class NotificationController {

    @Autowired
    private NotificationsDao notificationsDao;

    @RequestMapping(path="/{id}", method= RequestMethod.GET)
    public Notification getNotification(@PathVariable int id) {
        return notificationsDao.getNotification(id);
    }

    @RequestMapping(path="", method=RequestMethod.POST)
    public Notification createNotification(@RequestBody Notification notification) {
        return notificationsDao.createNotification(notification);
    }

}
