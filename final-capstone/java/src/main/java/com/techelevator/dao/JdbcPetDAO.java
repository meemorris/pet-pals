package com.techelevator.dao;

import com.techelevator.model.PetDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class JdbcPetDAO implements PetDAO {

    private JdbcTemplate jdbcTemplate;

    public JdbcPetDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean create(PetDTO petDTO, int userId) {
        boolean petCreated = false;

        // create pet
        String sql = "INSERT INTO pets (name, user_id, species, breed, weight, birth_year, " +
                "energetic_relaxed, shy_friendly, apathetic_curious, bio) VALUES(?,?,?,?,?,?,?,?,?,?)";
        petCreated = jdbcTemplate.update(sql, petDTO.getName(), userId, petDTO.getSpecies(), petDTO.getBreed(),
                petDTO.getWeight(), petDTO.getBirthYear(), petDTO.getEnergeticRelaxed(), petDTO.getShyFriendly(),
                petDTO.getApatheticCurious(), petDTO.getBio())==1;


        return petCreated;
    }

}
