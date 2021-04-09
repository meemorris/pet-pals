package com.techelevator.model;

import org.hibernate.validator.internal.util.privilegedactions.LoadClass;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class Playdate {

    private long playdateId;

    private long petId;

    private String address;

    private String city;

    private String state;

    private String zip;

    private LocalDateTime date;

    public Playdate(){}
    public Playdate(long playdateId, long petId, String address, String city, String state, String zip, LocalDateTime date) {
        this.playdateId = playdateId;
        this.petId = petId;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.date = date;
    }

    public Playdate(long playdateId) {
        this.playdateId = playdateId;
    }


    public long getPetId() {
        return petId;
    }

    public void setPetId(long petId) {
        this.petId = petId;
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
}


