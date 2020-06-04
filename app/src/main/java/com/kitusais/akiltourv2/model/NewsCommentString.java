package com.kitusais.akiltourv2.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewsCommentString {
    private String id;
    private String initiator;
    private String message;
    private String date;

    public NewsCommentString() {
    }

    public NewsCommentString(String id, String initiator, String message) {
        this.id = id;
        this.initiator = initiator;
        this.message = message;
        this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public NewsCommentString(String id, String initiator, String message, String date) {
        this.id = id;
        this.initiator = initiator;
        this.message = message;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
