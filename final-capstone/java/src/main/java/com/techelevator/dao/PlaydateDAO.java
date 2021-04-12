package com.techelevator.dao;

import com.techelevator.model.CreatePlaydateDTO;
import com.techelevator.model.Playdate;
import com.techelevator.services.MapService;

import java.util.List;

public interface PlaydateDAO {
    long create(CreatePlaydateDTO playdateDTO, MapService mapService);

    Playdate getPlaydate(int id);

    List<Playdate> getAllPlaydates();
}
