package com.techelevator.dao;

import com.techelevator.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcFlightDao implements FlightDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Flight> getFlights(int tripId) {
        List<Flight> flights = new ArrayList<>();

        String sql = "SELECT flight.flight_id, flight_number, destination, \n" +
                "airline, departure_time, departure_date, expected_arrival_time, \n" +
                "expected_arrival_date, estimated_arrival_time, estimated_arrival_date, \n" +
                "status, airplane_type, cost\n" +
                "FROM flight\n" +
                "JOIN trip_flight\n" +
                "\tON flight.flight_id = trip_flight.flight_id\n" +
                "WHERE trip_flight.trip_id = ?;";

        try{

            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, tripId);

            while(results.next()){
                flights.add(mapRowToFlight(results));
            }

        }catch(Exception ex){
            System.out.println("Something went wrong: " + ex.getMessage());
        }

        return flights;
    }

    public Flight getFlight(int flightId){

        Flight flight = null;

        String sql = "SELECT flight_id, flight_number, destination, \n" +
                "airline, departure_time, departure_date, expected_arrival_time, \n" +
                "expected_arrival_date, estimated_arrival_time, estimated_arrival_date, \n" +
                "status, airplane_type, cost\n" +
                "FROM flight\n" +
                "WHERE flight_id = ?;";

        try{

            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, flightId);

            while(results.next()){
                flight = mapRowToFlight(results);
            }

        }catch(Exception ex){
            System.out.println("Something went wrong: " + ex.getMessage());
        }

        return flight;
    }

    public Flight createFlight(Flight flight, int tripId) {

        Flight newFlight = null;

        String sql = "INSERT INTO flight(\n" +
                "\tflight_number, destination, airline, departure_time, departure_date, " +
                "expected_arrival_time, expected_arrival_date, estimated_arrival_time, " +
                "estimated_arrival_date, status, airplane_type, cost)\n" +
                "\tVALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING flight_id;";

        try{

            int flightId = jdbcTemplate.queryForObject(sql, int.class, flight.getFlightNumber(),
                    flight.getDestination(), flight.getAirline(), flight.getDepartureTime(),
                    flight.getDepartureDate(), flight.getExpectedArrivalTime(), flight.getExpectedArrivalDate(),
                    flight.getEstimatedArrivalTime(), flight.getEstimatedArrivalDate(), flight.getStatus(),
                    flight.getAirplaneType(), flight.getCost());

            addFlightToTrip(flightId, tripId);

            newFlight = getFlight(flightId);

        }catch(Exception ex){
            System.out.println("Something went wrong: " + ex.getMessage());
        }

        return newFlight;
    }

    private void addFlightToTrip(int flightId, int tripId) {

        String sql = "INSERT INTO trip_flight(trip_id, flight_id)\n" +
                "\tVALUES (?, ?);";

        try{

            jdbcTemplate.update(sql, tripId, flightId);

        }catch(Exception ex){
            System.out.println("Something went wrong: " + ex.getMessage());
        }

    }

    private Flight mapRowToFlight(SqlRowSet results){

        Flight flight = new Flight();

        flight.setAirline(results.getString("airline"));
        flight.setAirplaneType(results.getString("airplane_type"));
        flight.setCost(results.getBigDecimal("cost"));

        if(results.getDate("departure_date") != null) {
            flight.setDepartureDate(results.getDate("departure_date").toLocalDate());
        }

        flight.setDepartureTime(results.getString("departure_time"));

        if(results.getDate("estimated_arrival_date") != null) {
            flight.setEstimatedArrivalDate(results.getDate("estimated_arrival_date").toLocalDate());
        }

        flight.setEstimatedArrivalTime(results.getString("estimated_arrival_time"));

        if(results.getDate("expected_arrival_date") != null) {
            flight.setExpectedArrivalDate(results.getDate("expected_arrival_date").toLocalDate());
        }
        flight.setExpectedArrivalTime(results.getString("expected_arrival_time"));

        flight.setFlightId(results.getInt("flight_id"));
        flight.setFlightNumber(results.getString("flight_number"));
        flight.setStatus(results.getString("status"));

        return flight;

    }
}
