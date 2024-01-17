package com.techelevator.dao;

import com.techelevator.model.Trip;

import java.util.List;

public interface TripDao {
    Trip createTrip(String name, int userId);
    Trip getTrip(int tripId);

    List<Trip> getTrips(int userId);
}
