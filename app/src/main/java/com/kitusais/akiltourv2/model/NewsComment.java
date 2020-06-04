package com.kitusais.akiltourv2.model;

import java.util.Date;

public class NewsComment {
    private String id;
    private String initiator;
    private String message;
    private Date date;

    public NewsComment() {
    }

    public NewsComment(String id, String initiator, String message) {
        this.id = id;
        this.initiator = initiator;
        this.message = message;
        this.date = new Date();
    }

    public NewsComment(String id, String initiator, String message, Date date) {
        this.id = id;
        this.initiator = initiator;
        this.message = message;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
