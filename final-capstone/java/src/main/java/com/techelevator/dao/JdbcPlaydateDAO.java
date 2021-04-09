package com.techelevator.dao;

import com.techelevator.model.CreatePlaydateDTO;
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
    public CreatePlaydateDTO getPlaydate(int id) {
        CreatePlaydateDTO dto = new CreatePlaydateDTO();
        String sql = "SELECT pet_id, address, city, state, zip, date FROM playdates WHERE playdate_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if(results.next()) {
            dto = mapRowToPlaydate(results);
        }
        return dto;
    }

    private CreatePlaydateDTO mapRowToPlaydate(SqlRowSet results) {
        CreatePlaydateDTO dto = new CreatePlaydateDTO();
        dto.setPetId(results.getLong("pet_id"));
        dto.setAddress(results.getString("address"));
        dto.setCity(results.getString("city"));
        dto.setState(results.getString("state"));
        dto.setZip(results.getString("zip"));
        dto.setDate(results.getTimestamp("date"));
        return dto;
    }
}
