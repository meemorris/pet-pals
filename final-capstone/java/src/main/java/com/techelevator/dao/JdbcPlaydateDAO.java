package com.techelevator.dao;

import com.techelevator.model.CreatePlaydateDTO;
import com.techelevator.model.Location;
import com.techelevator.model.Playdate;
import com.techelevator.services.MapService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPlaydateDAO implements PlaydateDAO {

    private JdbcTemplate jdbcTemplate;
    private PetDAO petDAO;

    public JdbcPlaydateDAO(JdbcTemplate jdbcTemplate, PetDAO petDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.petDAO = petDAO;

    }


    @Override
    public long create(CreatePlaydateDTO playdateDTO, MapService mapService) {

        //get lat and lng
        Location location = mapService.getLocation(playdateDTO.getAddress(), playdateDTO.getCity(), playdateDTO.getState());

        //create playdate
        String sql = "INSERT INTO playdates (pet_id, address, city, state, zip, date, lat, lng) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?) RETURNING playdate_id";
        Long playdateId = jdbcTemplate.queryForObject(sql, Long.class, playdateDTO.getPet().getPetId(), playdateDTO.getAddress(),
                playdateDTO.getCity(), playdateDTO.getState(), playdateDTO.getZip(), playdateDTO.getDate(), location.getLat(), location.getLng());

        return playdateId;
    }

    @Override
    public Playdate getPlaydate(int id) {
        Playdate playdate = new Playdate();
        String sql = "SELECT playdate_id, pet_id, address, city, state, zip, date, lat, lng FROM playdates WHERE playdate_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if(results.next()) {
            playdate = mapRowToPlaydate(results);
        }
        return playdate;
    }

    @Override
    public List<Playdate> getAllPlaydates() {
        List<Playdate> playdates = new ArrayList<>();

        String sql = "SELECT playdate_id, pet_id, address, city, state, zip, date, lat, lng FROM playdates";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            playdates.add(mapRowToPlaydate(results));
        }
        return playdates;
    }


    private Playdate mapRowToPlaydate(SqlRowSet results) {
        Playdate playdate = new Playdate();
        playdate.setPlaydateId(results.getLong("playdate_id"));
        int petId = results.getInt("pet_id");
        playdate.setPet(petDAO.getPet(petId));
        playdate.setAddress(results.getString("address"));
        playdate.setCity(results.getString("city"));
        playdate.setState(results.getString("state"));
        playdate.setZip(results.getString("zip"));
        playdate.setDate(results.getTimestamp("date").toLocalDateTime());
        playdate.setLat(results.getString("lat"));
        playdate.setLng(results.getString("lng"));
        return playdate;
    }
}
