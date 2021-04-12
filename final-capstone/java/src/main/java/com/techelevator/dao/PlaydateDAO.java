package com.techelevator.dao;

import com.techelevator.model.CreatePlaydateDTO;
import com.techelevator.model.Playdate;

import java.util.List;

public interface PlaydateDAO {
    long create(CreatePlaydateDTO playdateDTO);

    Playdate getPlaydate(int id);

    List<Playdate> getAllPlaydates();

    long joinPlaydate(int petId, int playdateId);
}
