package com.techelevator.dao;

import com.techelevator.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTripDao implements TripDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Trip createTrip(String name, int userId) {
        //Step 1 - create a variable we will later return
        Trip newTrip = null;

        //Step 2 - define our sql
        String sql = "INSERT INTO trip(trip_name)\n" +
                "VALUES(?) RETURNING trip_id;";

        //Step 3 - send the sql to the database
        try{

            int tripId = jdbcTemplate.queryForObject(sql, int.class, name);

            addUserToTrip(userId, tripId);

            //Step 4 - populate the object we are returning
            newTrip = new Trip();
            newTrip.setTripId(tripId);
            newTrip.setTripName(name);

        } catch(Exception ex){
            System.out.println("Something went wrong");
        }

        //Step 5
        return newTrip;
    }

    private void addUserToTrip(int userId, int tripId){

        String sql = "INSERT INTO users_trip(trip_id, user_id)\n" +
                "VALUES(?,?);";

        try{
            jdbcTemplate.update(sql, tripId, userId);

        } catch(Exception ex){
            System.out.println("Something went wrong: " + ex.getMessage());
        }

    }

    @Override
    public Trip getTrip(int tripId) {
        //Step 1
        Trip trip = null;

        //Step 2
        String sql = "SELECT trip_id, trip_name\n" +
                "FROM trip\n" +
                "WHERE trip_id = ?;";

        //Step 3, send the sql to the database
        try{

            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, tripId);

            //Step 4 - convert results to objects
            if(results.next()){

                trip = mapRowToTrip(results);

            }

        } catch(Exception ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }

        //Step 5 - return
        return trip;
    }

    private Trip mapRowToTrip(SqlRowSet results){

        Trip trip = new Trip();

        trip.setTripId( results.getInt("trip_id") );
        trip.setTripName( results.getString("trip_name"));

        return trip;
    }

    @Override
    public List<Trip> getTrips(int userId) {
        //step 1
        List<Trip> trips = new ArrayList<>();

        //Step 2
        String sql = "SELECT trip.trip_id, trip_name\n" +
                "FROM trip\n" +
                "JOIN users_trip ON trip.trip_id = users_trip.trip_id\n" +
                "WHERE users_trip.user_id = ?;";

        //Step 3
        try{

            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

            //Step 4
            while(results.next()){
                Trip trip = mapRowToTrip(results);
                trips.add(trip);
            }

        } catch(Exception ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }

        //Step 5
        return trips;
    }
}
