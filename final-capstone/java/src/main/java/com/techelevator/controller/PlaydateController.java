package com.techelevator.controller;

import com.techelevator.dao.PlaydateDAO;
import com.techelevator.model.CreatePlaydateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
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
    public CreatePlaydateDTO getPlaydate(@PathVariable int id) {
        return playdateDAO.getPlaydate(id);
    }
}