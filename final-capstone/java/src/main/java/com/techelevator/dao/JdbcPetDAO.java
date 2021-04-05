package com.techelevator.dao;

import com.techelevator.model.PetDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcPetDAO implements PetDAO {

    private JdbcTemplate jdbcTemplate;

    public JdbcPetDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean create(PetDTO petDTO) {
        boolean petCreated = false;

        // create pet
        String insertPet = "INSERT INTO pets (name, user_id, species, breed, weight, birth_year" +
                "energetic_relaxed, shy_friendly, apathetic_curious, bio"

        return petCreated;
    }

}
