package com.techelevator.controller;

import com.techelevator.dao.PlaydateDAO;
import com.techelevator.model.CreatePlaydateDTO;
import com.techelevator.model.Playdate;
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

    public PlaydateController(PlaydateDAO playdateDAO) {
        this.playdateDAO = playdateDAO;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/playdates", method = RequestMethod.POST)
    public long create(@Valid @RequestBody CreatePlaydateDTO playdateDTO) {
        return playdateDAO.create(playdateDTO);
    }

    @RequestMapping(path = "/playdates/{id}", method = RequestMethod.GET)
    public Playdate getPlaydate(@PathVariable int id) {
        return playdateDAO.getPlaydate(id);
    }

    @RequestMapping(path = "/playdates", method = RequestMethod.GET)
    public List<Playdate> getAllPlaydates() {
        return playdateDAO.getAllPlaydates();
    }
}