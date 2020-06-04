package com.kitusais.akiltourv2.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kitusais.akiltourv2.model.News;
import com.kitusais.akiltourv2.model.NewsComment;
import com.kitusais.akiltourv2.model.Player;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class FirebaseCommentDatabaseHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private List<NewsComment> commentsTab = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded();
        //void DataIsInserted(List<Player> joueurs, List<String> keys);
//        void DataIsInserted(List<News> newsTab, List<String> keys);
        void DataIsInserted(List<NewsComment> commentsTab, List<String> keys);
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseCommentDatabaseHelper(String newsPath) {
        mDatabase = FirebaseDatabase.getInstance();
//        newsPath = "Test 2020-05-28 13:38:57";
        mReference = mDatabase.getReference("news").child(newsPath).child("comments");
    }

//    public void readNews(final DataStatus dataStatus){
//        mReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                newsTab.clear();
//                List<String> keys = new ArrayList<>();
//
//                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
//                    keys.add(keyNode.getKey());
//                    //keyNode.getChildren().iterator();
//                    Iterator<DataSnapshot> newsValues = keyNode.getChildren().iterator();
//                    Log.i("FirebaseDatabaseHelper","enumerating values "+keyNode.child("id").getValue().toString());
//                    //
//                    // Extraction de chaque attribut pour instancier l'objet
//                    String id = keyNode.child("id").getValue().toString();
//                    String date = keyNode.child("date").getValue().toString();
//                    String group = keyNode.child("group").getValue().toString();
////                    try {
////                        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(keyNode.child("date").getValue().toString());
////                    } catch (ParseException e) {
////                        e.printStackTrace();
////                    }
//                    String initiator = keyNode.child("initiator").getValue().toString();
//                    String message = keyNode.child("message").getValue().toString();
//                    //
//                    // S'il y a des commentaires
//                    News news = null;
//                    if(null != keyNode.child("comments").getValue()){
//                        Iterator<DataSnapshot> comments = keyNode.child("comments").getChildren().iterator();
//                        ArrayList<NewsComment> listComments = new ArrayList<>();
////                        Log.i("FirebaseDatabaseHelper","comment message - "+keyNode.child("comments").child("message").getValue().toString());
//                        while (comments.hasNext()){
//                            DataSnapshot comment = comments.next();
//                            Log.i("FirebaseDatabaseHelper","comment message - "+comment.child("message").getValue().toString());
//
//                            NewsComment mComment = new NewsComment(
//                                    comment.child("id").getValue().toString(),
//                                    comment.child("initiator").getValue().toString(),
//                                    comment.child("message").getValue().toString());
//                            listComments.add(mComment);
//                        }
//                        news = new News(date, group, id, initiator, message, listComments);
//                        Log.i("FirebaseDatabaseHelper","comment message - "+news.getComments().get(0).getMessage());
//                    }else{
//                        news = new News(date, group, id, initiator, message);
//                    }
//
//
//
////                    while(newsValues.hasNext()){
////                        DataSnapshot item = newsValues.next();
////
////                        Log.i("FirebaseDatabaseHelper message",item.child("message").toString());
////                        Log.i("FirebaseDatabaseHelper item",item.getKey()+" - "+item.getValue());
//////                        Log.i("FirebaseDatabaseHelper value",item.child("message").getValue().toString());
////                    }
////                    News news = keyNode.getValue(News.class);     // pour créer automatiquement l'objet -> ne fonctionne pas quand il y a des List dedans
//                    //if(news.getGroup() == -1){
//                    newsTab.add(news);
//                    //}
////                    if(news.getGameTimer() != 0) {
////                        int i=0;
////                        for(i=0; i< FirebaseDatabaseHelper.this.news.size(); i++){
////                            if(news.getGameTimer() > FirebaseDatabaseHelper.this.news.get(i).getGameTimer()){
////                                break;
////                            }
////                        }x²
////
////                        FirebaseDatabaseHelper.this.news.add(i,news);
////                    }
//                }
//                ArrayList<News> tab = new ArrayList<>();
//                while (newsTab.size() > 0 ){
//                    News cur = newsTab.get(0);
//                    for (News var : newsTab) {
//                        if(var.getDate().after(cur.getDate())){
//                            cur = var;
//                        }
//                    }
//                    tab.add(cur);
//                    newsTab.remove(cur);
//                }
//                dataStatus.DataIsInserted(tab, keys);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    public void readComments(final DataStatus dataStatus) {

        Log.i("FirebaseCommentDatabaseHelper","mReference "+mReference.toString());
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentsTab.clear();
                List<String> keys = new ArrayList<>();

                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    //keyNode.getChildren().iterator();
                    Iterator<DataSnapshot> commentsValues = keyNode.getChildren().iterator();
                    Log.i("FirebaseDatabaseHelper","id values "+keyNode.child("id").getValue().toString());
                    //
                    // Extraction de chaque attribut pour instancier l'objet
                    String id = keyNode.child("id").getValue().toString();
                    Date date = null;
                    try {
                        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(keyNode.child("date").getValue().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
//                    String group = keyNode.child("group").getValue().toString();
//                    try {
//                        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(keyNode.child("date").getValue().toString());
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
                    String initiator = keyNode.child("initiator").getValue().toString();
                    String message = keyNode.child("message").getValue().toString();
                    //
                    // S'il y a des commentaires(String id, String initiator, String message, Date date)
                    NewsComment comment = new NewsComment(id, initiator, message, date);
//                    if(null != keyNode.child("comments").getValue()){
//                        Iterator<DataSnapshot> comments = keyNode.child("comments").getChildren().iterator();
//                        ArrayList<NewsComment> listComments = new ArrayList<>();
////                        Log.i("FirebaseDatabaseHelper","comment message - "+keyNode.child("comments").child("message").getValue().toString());
//                        while (comments.hasNext()){
//                            DataSnapshot comment = comments.next();
//                            Log.i("FirebaseDatabaseHelper","comment message - "+comment.child("message").getValue().toString());
//
//                            NewsComment mComment = new NewsComment(
//                                    comment.child("id").getValue().toString(),
//                                    comment.child("initiator").getValue().toString(),
//                                    comment.child("message").getValue().toString());
//                            listComments.add(mComment);
//                        }
//                        news = new News(date, group, id, initiator, message, listComments);
//                        Log.i("FirebaseDatabaseHelper","comment message - "+news.getComments().get(0).getMessage());
//                    }else{
//                        news = new News(date, group, id, initiator, message);
//                    }



//                    while(newsValues.hasNext()){
//                        DataSnapshot item = newsValues.next();
//
//                        Log.i("FirebaseDatabaseHelper message",item.child("message").toString());
//                        Log.i("FirebaseDatabaseHelper item",item.getKey()+" - "+item.getValue());
////                        Log.i("FirebaseDatabaseHelper value",item.child("message").getValue().toString());
//                    }
//                    News news = keyNode.getValue(News.class);     // pour créer automatiquement l'objet -> ne fonctionne pas quand il y a des List dedans
                    //if(news.getGroup() == -1){
                    commentsTab.add(comment);
                    //}
//                    if(news.getGameTimer() != 0) {
//                        int i=0;
//                        for(i=0; i< FirebaseDatabaseHelper.this.news.size(); i++){
//                            if(news.getGameTimer() > FirebaseDatabaseHelper.this.news.get(i).getGameTimer()){
//                                break;
//                            }
//                        }x²
//
//                        FirebaseDatabaseHelper.this.news.add(i,news);
//                    }
                }
                ArrayList<NewsComment> tab = new ArrayList<>();
                while (commentsTab.size() > 0 ){
                    NewsComment cur = commentsTab.get(0);
                    for (NewsComment var : commentsTab) {
                        if(var.getDate().after(cur.getDate())){
                            cur = var;
                        }
                    }
                    tab.add(cur);
                    commentsTab.remove(cur);
                }
                dataStatus.DataIsInserted(tab, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

