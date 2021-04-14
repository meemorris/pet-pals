package com.techelevator.model;

import javax.validation.constraints.NotNull;

public class MessageDTO {

    @NotNull
    private int message_id;
    @NotNull
    private String message;
    @NotNull
    private int userId;

    public MessageDTO(@NotNull int message_id, @NotNull String message, @NotNull int userId) {
        this.message_id = message_id;
        this.message = message;
        this.userId = userId;
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
