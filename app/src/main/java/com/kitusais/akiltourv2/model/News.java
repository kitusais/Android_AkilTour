package com.kitusais.akiltourv2.model;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class News {

    private String id;
    private String initiator;
    private Date date;
    private String message;
    private String title;
    private int group;
    private ArrayList<NewsComment> comments;

    public News() {
    }

    public News(String id, String initiator, Date date, String message, String title) {
        this.id = id;
        this.initiator = initiator;
        this.date = date;
        this.message = message;
        this.title = title;
        this.group = -1;
    }
    public News(String date, String group, String id, String initiator, String message) {

        Log.i("News constuctor","id : "+id);
        this.id = id;
//        this.id = Integer.getInteger(id);
        this.initiator = initiator;
        try {
            this.date =(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(date) ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.message = message;
        this.title = "ddeedd";
        this.group = Integer.parseInt(group);
    }

    public News(String date, String group, String initiator, String message) {

        Log.i("News constuctor","id : "+id);
        try {
            this.id = initiator+(new SimpleDateFormat("yyyyMMddHHmmss").format((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(date)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        this.id = Integer.getInteger(id);
        this.initiator = initiator;
        try {
            this.date =(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(date) ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.message = message;
        this.title = "ddeedd";
        this.group = Integer.parseInt(group);
    }

    public News(String date, String group, String id, String initiator, String message, ArrayList<NewsComment> comments) {
        this.id = id;
        this.initiator = initiator;
        try {
            this.date =(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(date) ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.message = message;
        this.title = "ddeedd";
        this.group = Integer.parseInt(group);
        this.comments = comments;
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

    public Date getDate() {
        return date;
    }

    public void setDateDate(Date date) {
        this.date = date;
//        try {
//            this.date =(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(date) ;
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }
    public void setDate(String date) {
//        this.date = date;
        try {
            this.date =(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(date) ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public ArrayList<NewsComment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<NewsComment> comments) {
        this.comments = comments;
    }

    public void addComment(NewsComment comment){
        this.comments.add(comment);
    }
}
