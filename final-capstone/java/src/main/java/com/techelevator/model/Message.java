package com.techelevator.model;

public class Message {

    private long messageId;
    private long userId;
    private String message;

    public Message() {}

    public Message(long messageId, long userId, String message) {
        this.messageId = messageId;
        this.userId = userId;
        this.message = message;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
