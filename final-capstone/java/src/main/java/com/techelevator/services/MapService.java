package com.techelevator.services;

import com.techelevator.model.Location;

public interface MapService {

    Location getLocation(String address, String city, String state);
}
