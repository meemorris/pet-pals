package com.techelevator.dao;

import com.techelevator.model.MessageDTO;
import com.techelevator.model.Message;

import java.util.List;

public interface MessageDAO {

    Message getMessage (int id);

    List<Message> getMessageByUserId(int userId);

    long create(MessageDTO messageDTO, int userId);
}
