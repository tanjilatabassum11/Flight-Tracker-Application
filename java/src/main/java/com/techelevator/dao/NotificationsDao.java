package com.techelevator.dao;

import com.techelevator.model.Notification;

import java.util.List;

public interface NotificationsDao {

    public List<Notification> getNotifications(int tripId) ;

    public Notification getNotification(int notificationId);

    public Notification createNotification(Notification notification) ;
}
