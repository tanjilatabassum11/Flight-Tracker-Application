package com.techelevator.services;

import com.techelevator.model.FlightSearchResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/*

    The reason we put our service in our server code instead of in our front end code
    is because on the front end everything can be seen through developer tools and we don't
    want a malicious actor seeing our access key for this api and then abusing it so that
    our account gets shut down. Instead we do it here from the server and our access key
    is protected.

 */

@Component
public class AviationService {

    private final String BASE_URL = "http://api.aviationstack.com/v1/flights?access_key=16d0124041c17b20c38308d3cc9717bc";

    private RestTemplate restTemplate = new RestTemplate();

    public String getFlightStatus(String number) {

        String url = BASE_URL + "&flight_icao=" + number;

        FlightSearchResult result = restTemplate.getForObject(url, FlightSearchResult.class);

        return result.getData().get(0).getFlightStatus();

    }
}
