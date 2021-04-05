package com.techelevator.controller;

import com.techelevator.dao.PetDAO;
import com.techelevator.dao.UserDAO;
import com.techelevator.model.PetDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@CrossOrigin
public class PetController {

    private PetDAO petDAO;
    private UserDAO userDAO;

    public PetController(PetDAO petDAO, UserDAO userDAO) {
        this.petDAO = petDAO;
        this.userDAO = userDAO;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/pets/register", method = RequestMethod.POST)
    public void register(@Valid @RequestBody PetDTO petDTO, Principal principal) {
        int userId = userDAO.findIdByUsername(principal.getName());
        petDAO.create(petDTO, userId);
    }
}
