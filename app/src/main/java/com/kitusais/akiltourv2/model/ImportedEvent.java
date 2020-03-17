package com.kitusais.akiltourv2.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ImportedEvent {

    private int id;
    private String initiator;
    private Date date;
    private String message;

    public ImportedEvent() {
    }

    public ImportedEvent(int id, String initiator, Date date, String message) {
        this.id = id;
        this.initiator = initiator;
        this.date = date;
        this.message = message;
    }
    public ImportedEvent( String initiator, Date date, String message) {
        this.initiator = initiator;
        this.date = date;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime(){
        return new SimpleDateFormat("hh:mm:ss").format(this.date);
    }

    @Override
    public String toString() {
        return "ImportedEvent{" +
                "id=" + id +
                ", initiator='" + initiator + '\'' +
                ", date=" + date +
                ", message='" + message + '\'' +
                '}';
    }
}
