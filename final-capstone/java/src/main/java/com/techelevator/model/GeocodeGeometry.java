package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodeGeometry {
    @JsonProperty("location")
    Location geocodeLocation;
    public GeocodeGeometry() {
    }
    public Location getLocation() {
        return geocodeLocation;
    }
    public void setGeocodeLocation(Location geocodeLocation) {
        this.geocodeLocation = geocodeLocation;
    }
}
