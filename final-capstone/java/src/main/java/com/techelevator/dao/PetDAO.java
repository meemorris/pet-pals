package com.techelevator.dao;

import com.techelevator.model.Attendee;
import com.techelevator.model.Pet;
import com.techelevator.model.PetDTO;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.security.Principal;
import java.util.List;

public interface PetDAO {

    long create(PetDTO petDTO, int userId);

    Pet getPet(int id);

    void updatePet(int id, PetDTO petDTO);

    List<Pet> getPetsByUserId(int userId);

    Pet mapRowToPet(SqlRowSet results);
}

