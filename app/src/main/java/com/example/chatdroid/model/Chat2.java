package com.example.chatdroid.model;

import java.util.Objects;

public class Chat2 {

    private String sender;
    private String reciever;
    private String message;


    public Chat2(String message,String reciever,String sender) {
        this.message=message;
        this.reciever=reciever;
        this.sender=sender;
    }

    public Chat2() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
