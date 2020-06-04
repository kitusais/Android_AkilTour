package com.kitusais.akiltourv2.controler;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.kitusais.akiltourv2.R;
import com.kitusais.akiltourv2.dao.FirebaseCommentDatabaseHelper;
import com.kitusais.akiltourv2.dao.FirebaseDatabaseHelper;
import com.kitusais.akiltourv2.model.News;
import com.kitusais.akiltourv2.model.NewsComment;
import com.kitusais.akiltourv2.model.NewsCommentString;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.kitusais.akiltourv2.MainActivity.authPlayer;
import static com.kitusais.akiltourv2.MainAppActivity.database;

public class CommentController {

    static private ArrayList<NewsComment> comments;
    static private RecyclerView mRecyclerView;
    static Context context;
    public static void showComments(Context mContext, final News mNews){
        context = mContext;
        comments = new ArrayList<>();
        AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(true);
        final View addView = LayoutInflater.from(mContext).inflate(R.layout.news_comment_layout, null);
        mRecyclerView = addView.findViewById(R.id.recyclerview_comments);

        new FirebaseCommentDatabaseHelper(mNews.getId()).readComments(new FirebaseCommentDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded() {
            }

            @Override
//            public void DataIsInserted(List<News> news, List<String> keys) {
            public void DataIsInserted(List<NewsComment> comments, List<String> keys) {
//                for (News singleNews : news) {
//                    if (singleNews.getId() == mNews.getId()){
//                        comments = singleNews.getComments();
//                    }
//                }
                //news.clear();
//                if(news.size() == 0){
//                    for(int i=0; i<3; i++){
//                        int rand = 0 + (int)(Math.random() * ((10 - 0) + 1));
//                        news.add(new News());
//                        //Log.i("NewsFragment",""+keys.get(i));
//                        news.get(i).setDateDate(new Date());
//                        news.get(i).setInitiator("suerzefez");
//                        news.get(i).setMessage("messssssage !!!");
//                        news.get(i).setId(rand);
//                    }
//                }
                new RecyclerCommentView_Config().setConfig(mRecyclerView,context,
                        comments, keys);
//                if(countJoueurs < joueurs.size()){
//                    countJoueurs = joueurs.size();
//                    resultGagnant = GameControler.gagnant(joueurs, resultTW);
//
//                }
            }

//            @Override
//            public void DataCommentIsInserted(List<NewsComment> commentsTab, List<String> keys) {
//
//                new RecyclerCommentView_Config().setConfig(mRecyclerView,context,
//                        commentsTab, keys);
//            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

        TextView addCommentTitle = addView.findViewById(R.id.addCommentTitle);
        addCommentTitle.setText("Message de "+mNews.getInitiator());
        TextView addCommentMessage = addView.findViewById(R.id.addCommentMessage);
        addCommentMessage.setText(mNews.getMessage());
        Button addBtn = addView.findViewById(R.id.addCommentBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText addComment =  addView.findViewById(R.id.addCommentEditText);
                String comment =addComment.getText().toString();
                String id = authPlayer.getPseudo()+(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                NewsCommentString newsComment = new NewsCommentString(id,authPlayer.getPseudo(),comment);
                final DatabaseReference dbRef = database.getReference("news");
                dbRef.child(mNews.getId()).child("comments").child(id).setValue(newsComment);
                addComment.setText("");
            }
        });
        builder.setView(addView);
        alertDialog = builder.create();
        alertDialog.show();
    }
}
