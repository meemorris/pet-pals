package com.techelevator.dao;

import com.techelevator.model.CreatePlaydateDTO;

public interface PlaydateDAO {
    long create(CreatePlaydateDTO playdateDTO);

    CreatePlaydateDTO getPlaydate(int id);
}
