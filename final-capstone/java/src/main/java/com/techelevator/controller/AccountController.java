package com.techelevator.controller;

import com.techelevator.dao.UserDAO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@CrossOrigin
public class AccountController {

    private UserDAO userDAO;
    private AccountDAO accountDAO;

    public AccountController(UserDAO userDAO, AccountDAO accountDAO){
        this.userDAO = userDAO;
        this.accountDAO = accountDAO;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/profile", method = RequestMethod.POST)
    public void create(@Valid @RequestBody AccountDTO accountDTO, Principal principal) {
        int userId = userDAO.findIdByUsername(principal.getName());
        accountDAO.create(accountDTO, userId);
    }

    @RequestMapping(path = "/profile/{id}", method = RequestMethod.GET)
    public Account getAccount(@PathVariable int id) {
        return accountDAO.getAccount(id);
    }

    @RequestMapping(path = "/profile/{id}", method = RequestMethod.PUT)
    public void updateAccount(@PathVariable int id, @Valid @RequestBody AccountDTO accountDTO){
        accountDAO.updateAccount(id, accountDTO);
    }
}
