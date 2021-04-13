package com.techelevator.dao;

import com.techelevator.model.Account;
import com.techelevator.model.CreatePlaydateDTO;
import com.techelevator.model.Location;
import com.techelevator.model.Pet;
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
    private AttendeeDAO attendeeDAO;
    private AccountDAO accountDAO;
    private static final boolean IS_HOST = true;
    private static final boolean IS_NOT_HOST = false;

    public JdbcPlaydateDAO(JdbcTemplate jdbcTemplate, PetDAO petDAO, AttendeeDAO attendeeDAO, AccountDAO accountDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.petDAO = petDAO;
        this.attendeeDAO = attendeeDAO;
        this.accountDAO = accountDAO;
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

        //add to linker table
        String sqlPetPlaydate = "INSERT INTO playdates_pets (playdate_id, pet_id, is_host) VALUES(?,?,?)";
        jdbcTemplate.update(sqlPetPlaydate, playdateId, playdateDTO.getPet().getPetId(), IS_HOST);

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
    public List<Playdate> getAllPlaydates(MapService mapService, int userId) {
        List<Playdate> playdates = new ArrayList<>();

        String sql = "SELECT playdate_id, pet_id, address, city, state, zip, date, lat, lng FROM playdates";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        String destinations = "";
        while(results.next()) {
            Playdate playdate = mapRowToPlaydate(results);
            playdates.add(playdate);
            destinations += playdate.getLat() + "," + playdate.getLng() + "|";
        }
        destinations = destinations.substring(0,destinations.length()-1);

        //get location of current user
        Account currentAccount = accountDAO.getAccount(userId);
        String origin = currentAccount.getLat() + "," + currentAccount.getLng();

        //get distances from current user
        List<String> distances = mapService.getDistances(origin, destinations);
        for (int i = 0; i < distances.size(); i++){
            playdates.get(i).setDistanceFromUser(distances.get(i));
        }

        return playdates;
    }

    @Override
    public long joinPlaydate(int playdateId, int petId) {
        String sql = "INSERT INTO playdates_pets (playdate_id, pet_id, is_host) VALUES(?,?,?)";
        return jdbcTemplate.update(sql, playdateId, petId, IS_NOT_HOST);
    }

    @Override
    public List<Playdate> getScheduledPlaydates(int petId) {
        List<Playdate> playdates = new ArrayList<>();

        String sql = "SELECT p.playdate_id, p.pet_id, address, city, state, zip, date, lat, lng " +
        "FROM playdates p " +
        "JOIN playdates_pets pp ON p.playdate_id = pp.playdate_id " +
        "WHERE pp.pet_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, petId);
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
        playdate.setAttendeeList(attendeeDAO.getAttendees(results.getInt("playdate_id")));
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
