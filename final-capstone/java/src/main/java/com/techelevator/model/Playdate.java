package com.techelevator.model;

import java.time.LocalDateTime;

public class Playdate {

    private long playdateId;

    private Pet pet;

    private String address;

    private String city;

    private String state;

    private String zip;

    private LocalDateTime date;

    private Location location;

    public Playdate(){}
    public Playdate(long playdateId, Pet pet, String address, String city, String state, String zip, LocalDateTime date, Location location) {
        this.playdateId = playdateId;
        this.pet = pet;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.date = date;
        this.location = location;
    }

//    public Playdate(long playdateId) {
//        this.playdateId = playdateId;
//    }


    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public long getPlaydateId() {
        return playdateId;
    }

    public void setPlaydateId(long playdateId) {
        this.playdateId = playdateId;
    }


    public Location getCoordinates() {
        return location;
    }

    public void setCoordinates(Location location) {
        this.location = location;
    }
}


