package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlightResult {

    @JsonProperty("flight_status")
    private String flightStatus;

    private Arrival arrival;

    public String getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(String flightStatus) {
        this.flightStatus = flightStatus;
    }

    public Arrival getArrival() {
        return arrival;
    }

    public void setArrival(Arrival arrival) {
        this.arrival = arrival;
    }
}
