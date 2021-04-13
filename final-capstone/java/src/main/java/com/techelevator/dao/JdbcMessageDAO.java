package com.techelevator.dao;

import com.techelevator.model.MessageDTO;
import com.techelevator.model.Message;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JdbcMessageDAO implements MessageDAO  {

    private JdbcTemplate jdbcTemplate;

    public JdbcMessageDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Message getMessage(int id) {
        Message message = new Message();

        String sql = "SELECT message_id, user_id, message FROM messages WHERE message_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if(results.next()) {
                message = mapRowToMessage(results);
            }

            return message;
        }

    @Override
    public List<Message> getMessageByUserId(int userId) {
        return null;
    }

    @Override
    public long create(MessageDTO messageDTO, int userId) {

        String sql = "INSERT INTO messages (user_id, message) VALUES (?,?) RETURNING message_id";
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
