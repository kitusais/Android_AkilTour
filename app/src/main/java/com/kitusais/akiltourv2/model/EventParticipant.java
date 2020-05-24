package com.kitusais.akiltourv2.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class EventParticipant implements Parcelable {

    private int id_event;
    private Date date;
    private String message;
    private String username;
    private String decision;


    public EventParticipant(int id_event,Date date, String message, String username, String decision) {
        this.id_event = id_event;
        this.username = username;
        this.date = date;
        this.message = message;
        this.decision = decision;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id_event);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeString(this.message);
        dest.writeString(this.username);
    }

    protected EventParticipant(Parcel in) {
        this.id_event = in.readInt();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.message = in.readString();
        this.username = in.readString();
    }

    public static final Parcelable.Creator<EventParticipant> CREATOR = new Parcelable.Creator<EventParticipant>() {
        @Override
        public EventParticipant createFromParcel(Parcel source) {
            return new EventParticipant(source);
        }

        @Override
        public EventParticipant[] newArray(int size) {
            return new EventParticipant[size];
        }
    };
}
