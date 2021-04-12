package com.techelevator.services;

import com.techelevator.model.GeocodeResult;
import com.techelevator.model.Location;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestMapService implements MapService{

    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json?";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Location getLocation(String address, String city, String state) {
        String fullAddress = formatFullAddress(address, city, state);
        GeocodeResult geocodeResult = null;
        try {
            geocodeResult = restTemplate.getForObject(BASE_URL + "address=" +  fullAddress + "&key=AIzaSyCMDIDFLUZUj0iH6vHHsSb-hFw4ZQmhgus", GeocodeResult.class);
        } catch (RestClientResponseException ex) {
            System.out.println(ex.getRawStatusCode() + " : " + ex.getStatusText());
        } catch (ResourceAccessException ex) {
            System.out.println(ex.getMessage());
        }
        assert geocodeResult != null;

        return geocodeResult.getResults().get(0).getGeometry().getLocation();
    }

    private String formatFullAddress(String address, String city, String state){
        String fullAddress ="";
        String fixedAddress = String.join("+", address.split(" "));
        String fixedCity = String.join("+", city.split(" "));
        String fixedState= String.join("+", state.split(" "));
        fullAddress = fixedAddress + "+" + fixedCity + "+" + fixedState;

        return fullAddress;
    }
}
