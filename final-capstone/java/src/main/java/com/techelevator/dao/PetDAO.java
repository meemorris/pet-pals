package com.techelevator.dao;

import com.techelevator.model.PetDTO;

import java.security.Principal;

public interface PetDAO {

    boolean create(PetDTO petDTO, int userId);

}
