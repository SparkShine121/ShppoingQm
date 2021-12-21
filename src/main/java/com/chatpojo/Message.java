package com.chatpojo;

import java.util.Date;


//呃..
public class Message {
    private String toName;
    private String message;
    private String fromName;
    private Date dateTime;

    public Message() {

    }

    public Message(String toName, String message, String fromName, Date dateTime) {
        this.toName = toName;
        this.message = message;
        this.fromName = fromName;
        this.dateTime = dateTime;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
