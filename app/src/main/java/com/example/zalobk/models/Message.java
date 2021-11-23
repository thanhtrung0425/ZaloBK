package com.example.zalobk.models;

public class Message {

    String messages;
    String senderID;
    long timestamp;
    String currenttimme;

    public Message() {
    }

    public Message(String messages, String senderID, long timestamp, String currenttimme) {
        this.messages = messages;
        this.senderID = senderID;
        this.timestamp = timestamp;
        this.currenttimme = currenttimme;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getCurrenttimme() {
        return currenttimme;
    }

    public void setCurrenttimme(String currenttimme) {
        this.currenttimme = currenttimme;
    }
}
