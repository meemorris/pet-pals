package com.techelevator.dao;

import com.techelevator.model.Account;
import com.techelevator.model.AccountDTO;
import com.techelevator.services.MapService;

public interface AccountDAO {
    void create(AccountDTO accountDTO, int userId, MapService mapService);

    void updateAccount(int id, AccountDTO accountDTO);

    Account getAccount(int id);
}
