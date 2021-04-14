package com.techelevator.dao;

import com.techelevator.model.MessageDTO;
import com.techelevator.model.Message;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcMessageDAO implements MessageDAO  {

    private JdbcTemplate jdbcTemplate;

    public JdbcMessageDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Message getMessages() {
        Message message = new Message();

        String sql = "SELECT * FROM messages";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            if(results.next()) {
                message = mapRowToMessage(results);
            }

            return message;
        }

    @Override
    public List<Message> getMessageByUserId(int userId) {
        List<Message> messageList = new ArrayList<>();
        String sql = "SELECT * FROM messages WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while(results.next()) {
            messageList.add(mapRowToMessage(results));
        }
        return messageList;
    }

    @Override
    public long create(MessageDTO messageDTO, int userId) {

        String sql = "INSERT INTO messages (message, user_id) VALUES (?,?) RETURNING message_id";
        Long messageId = jdbcTemplate.queryForObject(sql, Long.class, messageDTO.getMessage(), userId);

        return messageId;
    }

    private Message mapRowToMessage(SqlRowSet results) {
        Message message = new Message();
        message.setMessageId(results.getLong("message_id"));
        message.setUserId(results.getLong("user_id"));
        message.setMessage(results.getString("message"));
        return message;
    }


}
