package com.techelevator.dao;

import com.techelevator.model.Flight;
import com.techelevator.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class JdbcNotificationsDao implements NotificationsDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Notification> getNotifications(int tripId) {

        List<Notification> notifications = new ArrayList<>();

        String sql = "SELECT notification_id, trip_id, notification_msg \n" +
                "FROM trip_notifications\n" +
                "WHERE trip_id = ?;";

        try{

            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, tripId);

            while(results.next()){
                notifications.add(mapRowToNotification(results));
            }

        }catch(Exception ex){
            System.out.println("Something went wrong: " + ex.getMessage());
        }

        return notifications;

    }

    public Notification getNotification(int notificationId){
        Notification notification = null;

        String sql = "SELECT notification_id, trip_id, notification_msg \n" +
                "FROM trip_notifications\n" +
                "WHERE notification_id = ?;";

        try{

            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, notificationId);

            while(results.next()){
                notification = mapRowToNotification(results);
            }

        }catch(Exception ex){
            System.out.println("Something went wrong: " + ex.getMessage());
        }

        return notification;
    }

    public Notification createNotification(Notification notification) {

        Notification newNotification = null;

        String sql = "INSERT INTO trip_notifications(\n" +
                "\ttrip_id, notification_msg)\n" +
                "\tVALUES (?, ?) RETURNING notification_id;";

        try{

            int id = jdbcTemplate.queryForObject(sql, int.class,
                        notification.getTripId(), notification.getMessage());

            newNotification = getNotification(id);

        }catch(Exception ex){
            System.out.println("Something went wrong: " + ex.getMessage());
        }

        return newNotification;

    }

    private Notification mapRowToNotification(SqlRowSet results){

        Notification note = new Notification();

        note.setMessage(results.getString("notification_msg"));
        note.setNotificationId(results.getInt("notification_id"));
        note.setTripId(results.getInt("trip_id"));

        return note;

    }

}
