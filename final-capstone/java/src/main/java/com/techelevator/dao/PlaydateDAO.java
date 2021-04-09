package com.techelevator.dao;

import com.techelevator.model.CreatePlaydateDTO;
import com.techelevator.model.Playdate;

public interface PlaydateDAO {
    long create(CreatePlaydateDTO playdateDTO);

    Playdate getPlaydate(int id);
}
