package com.techelevator.controller;

import com.techelevator.dao.AccountDAO;
import com.techelevator.dao.UserDAO;
import com.techelevator.model.Account;
import com.techelevator.model.AccountDTO;
import com.techelevator.services.MapService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@CrossOrigin
public class AccountController {

    private UserDAO userDAO;
    private AccountDAO accountDAO;
    private MapService mapService;

    public AccountController(UserDAO userDAO, AccountDAO accountDAO, MapService mapService){
        this.userDAO = userDAO;
        this.accountDAO = accountDAO;
        this.mapService = mapService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/profile", method = RequestMethod.POST)
    public void create(@Valid @RequestBody AccountDTO accountDTO, Principal principal) {
        int userId = userDAO.findIdByUsername(principal.getName());
        accountDAO.create(accountDTO, userId, mapService);
    }

    @RequestMapping(path = "/profile/{id}", method = RequestMethod.GET)
    public Account getAccount(@PathVariable int id) {
        return accountDAO.getAccount(id);
    }

    @RequestMapping(path = "/profile/{id}", method = RequestMethod.PUT)
    public void updateAccount(@PathVariable int id, @Valid @RequestBody AccountDTO accountDTO, MapService mapService){
        accountDAO.updateAccount(id, accountDTO, mapService);
    }
}
