package com.techelevator.dao;

import com.techelevator.model.Flight;

import java.util.List;

public interface FlightDao {
    public List<Flight> getFlights(int tripId);

    public Flight getFlight(int flightId);

    public Flight createFlight(Flight flight, int tripId);
}
