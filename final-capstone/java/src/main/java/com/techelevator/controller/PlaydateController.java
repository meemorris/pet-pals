package com.techelevator.controller;

import com.techelevator.dao.PlaydateDAO;
import com.techelevator.model.CreatePlaydateDTO;
import com.techelevator.model.Playdate;
import com.techelevator.services.MapService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class PlaydateController {

    private PlaydateDAO playdateDAO;
    private MapService mapService;

    public PlaydateController(PlaydateDAO playdateDAO, MapService mapService) {
        this.playdateDAO = playdateDAO;
        this.mapService = mapService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/playdates", method = RequestMethod.POST)
    public long create(@Valid @RequestBody CreatePlaydateDTO playdateDTO) {
        return playdateDAO.create(playdateDTO, mapService);
    }

    @RequestMapping(path = "/playdates/{id}", method = RequestMethod.GET)
    public Playdate getPlaydate(@PathVariable int id) {
        return playdateDAO.getPlaydate(id);
    }

    @RequestMapping(path = "/playdates", method = RequestMethod.GET)
    public List<Playdate> getAllPlaydates() {
        return playdateDAO.getAllPlaydates();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "{petId}/join/playdate/{playdateId}", method = RequestMethod.POST)
    public long addPetToPlaydate(@PathVariable int petId, @PathVariable int playdateId) {
        return playdateDAO.joinPlaydate(petId, playdateId);
    }

    @RequestMapping(path = "{petId}/schedule/playdates", method = RequestMethod.GET)
    public List<Playdate> getScheduledPlaydates(@PathVariable int petId) {
        return playdateDAO.getScheduledPlaydates(petId);
    }
}