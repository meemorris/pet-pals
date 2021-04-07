package com.techelevator.dao;

import com.techelevator.model.Pet;
import com.techelevator.model.PetDTO;

import java.security.Principal;

public interface PetDAO {

    boolean create(PetDTO petDTO, int userId);

    Pet getPet(int id);

    void updatePet(int id, PetDTO petDTO);
}
