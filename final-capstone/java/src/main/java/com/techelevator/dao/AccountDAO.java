package com.techelevator.dao;

import com.techelevator.model.Account;
import com.techelevator.model.AccountDTO;

public interface AccountDAO {
    void create(AccountDTO accountDTO, int userId);

    void updateAccount(int id, AccountDTO accountDTO);

    Account getAccount(int id);
}
