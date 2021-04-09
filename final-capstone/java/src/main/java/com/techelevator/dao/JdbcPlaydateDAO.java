package com.techelevator.dao;

import com.techelevator.model.CreatePlaydateDTO;
import com.techelevator.model.Playdate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcPlaydateDAO implements PlaydateDAO {

    private JdbcTemplate jdbcTemplate;

    public JdbcPlaydateDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }


    @Override
    public long create(CreatePlaydateDTO playdateDTO) {
        String sql = "INSERT INTO playdates (pet_id, address, city, state, zip, date) " +
                "VALUES(?, ?, ?, ?, ?, ?) RETURNING playdate_id";
        Long playdateId = jdbcTemplate.queryForObject(sql, Long.class, playdateDTO.getPetId(), playdateDTO.getAddress(),
                playdateDTO.getCity(), playdateDTO.getState(), playdateDTO.getZip(), playdateDTO.getDate());

        return playdateId;
    }

    @Override
    public Playdate getPlaydate(int id) {
        Playdate playdate = new Playdate();
        String sql = "SELECT playdate_id, pet_id, address, city, state, zip, date FROM playdates WHERE playdate_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if(results.next()) {
            playdate = mapRowToPlaydate(results);
        }
        return playdate;
    }

    private Playdate mapRowToPlaydate(SqlRowSet results) {
        Playdate playdate = new Playdate();
        playdate.setPlaydateId(results.getLong("playdate_id"));
        playdate.setPetId(results.getLong("pet_id"));
        playdate.setAddress(results.getString("address"));
        playdate.setCity(results.getString("city"));
        playdate.setState(results.getString("state"));
        playdate.setZip(results.getString("zip"));
        playdate.setDate(results.getTimestamp("date").toLocalDateTime());
        return playdate;
    }
}
