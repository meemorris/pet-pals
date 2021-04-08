package com.techelevator.dao;

import com.techelevator.model.Account;
import com.techelevator.model.AccountDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcAccountDAO implements AccountDAO{

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(AccountDTO accountDTO, int userId) {
        //create account
        String sql = "INSERT INTO accounts (user_id, first_name, last_name, email, phone, address, city, state, " +
                "zip, bio, pic) VALUES(?,?,?,?,?,?,?,?,?,?,?) ";
        jdbcTemplate.update(sql,  userId, accountDTO.getFirstName(), accountDTO.getLastName(), accountDTO.getEmail(),
                accountDTO.getPhone(), accountDTO.getAddress(), accountDTO.getCity(), accountDTO.getState(),
                accountDTO.getZip(), accountDTO.getBio(), accountDTO.getPic());



    }

    @Override
    public void updateAccount(int id, AccountDTO accountDTO) {
        String sql = "UPDATE accounts SET first_name = ?, last_name = ?, email = ?, phone = ?, address = ?, " +
                "city = ?, state = ?, zip = ?, bio = ?, pic = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, accountDTO.getFirstName(), accountDTO.getLastName(), accountDTO.getEmail(),
                accountDTO.getPhone(), accountDTO.getAddress(), accountDTO.getCity(), accountDTO.getState(),
                accountDTO.getZip(), accountDTO.getBio(), accountDTO.getPic(), id);

    }

    @Override
    public Account getAccount(int id) {
        Account account = new Account();

        String sql = "SELECT first_name, last_name, email, phone, address, city, state, " +
                "zip, bio, pic FROM accounts WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if(results.next()) {
            account = mapRowToAccount(results);
        }

        return account;
    }

    private Account mapRowToAccount(SqlRowSet results) {
        Account account = new Account();
        account.setFirstName(results.getString("first_name"));
        account.setLastName(results.getString("last_name"));
        account.setEmail(results.getString("email"));
        account.setPhone(results.getString("phone"));
        account.setAddress(results.getString("address"));
        account.setCity(results.getString("city"));
        account.setState(results.getString("state"));
        account.setZip(results.getString("zip"));
        account.setBio(results.getString("bio"));
        account.setPic(results.getString("pic"));
        return account;
    }
}
