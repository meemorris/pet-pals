package com.techelevator.dao;

import com.techelevator.model.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcMessageDAO implements MessageDAO  {

    private JdbcTemplate jdbcTemplate;
    private PetDAO petDAO;
    private AccountDAO accountDAO;
    private UserDAO userDAO;

    public JdbcMessageDAO(JdbcTemplate jdbcTemplate, PetDAO petDAO, AccountDAO accountDAO, UserDAO userDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.petDAO = petDAO;
        this.accountDAO = accountDAO;
        this.userDAO = userDAO;
    }

    @Override
    public List<Message> getMessages() {
        List<Message> messageList = new ArrayList<>();

        String sql = "SELECT message_id, m.user_id, message, posted_date, m.pet_id, username, a.pic AS account_pic, " +
                "name, pets.user_id, species, breed, weight, birth_year, energetic_relaxed, shy_friendly, apathetic_curious, pets.bio, pets.pic " +
                "FROM messages m " +
                "JOIN users u ON u.user_id=m.user_id " +
                "JOIN accounts a ON a.user_id=m.user_id " +
                "LEFT JOIN pets ON pets.pet_id=m.pet_id";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while(results.next()) {
                messageList.add(mapRowToMessage(results));
            }

            return messageList;
        }

//    @Override
//    public List<Message> getMessageByUserId(int userId) {
//        List<Message> messageList = new ArrayList<>();
//        String sql = "SELECT * FROM messages WHERE user_id = ?";
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
//        while(results.next()) {
//            messageList.add(mapRowToMessage(results));
//        }
//        return messageList;
//    }

    @Override
    public long create(MessageDTO messageDTO, int userId, LocalDateTime postedDate) {
        Long messageId;

        if (messageDTO.getPetId()!=0) {

            String sql = "INSERT INTO messages (user_id, message, posted_date, pet_id) VALUES (?,?,?,?) RETURNING message_id";
            messageId = jdbcTemplate.queryForObject(sql, Long.class, userId, messageDTO.getMessage(), postedDate, messageDTO.getPetId());
        } else {
            String sql = "INSERT INTO messages (user_id, message, posted_date) VALUES (?,?,?) RETURNING message_id";
            messageId = jdbcTemplate.queryForObject(sql, Long.class, userId, messageDTO.getMessage(), postedDate);
        }

        return messageId;
    }

    private Message mapRowToMessage(SqlRowSet results) {
        Message message = new Message();
        message.setMessageId(results.getLong("message_id"));
        message.setUserId(results.getLong("user_id"));
        message.setMessage(results.getString("message"));
        message.setPostedDate(results.getTimestamp("posted_date").toLocalDateTime());
        Pet pet = petDAO.mapRowToPet(results);
        message.setPet(pet);
        message.setName(results.getString("username"));
        message.setPic(results.getString("account_pic"));
        return message;
    }


}
