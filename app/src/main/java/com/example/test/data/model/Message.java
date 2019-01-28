package com.example.test.data.model;

public class Message {

    private String id;
    private boolean isMyMessage;
    private String text;

    public Message(String id, boolean is_my_message, String text) {
        this.id = id;
        this.isMyMessage = is_my_message;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isMyMessage() {
        return isMyMessage;
    }

    public void setMyMessage(boolean myMessage) {
        this.isMyMessage = myMessage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
