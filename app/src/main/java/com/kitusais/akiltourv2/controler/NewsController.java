package com.kitusais.akiltourv2.controler;

import android.util.Log;
import android.view.View;

import com.kitusais.akiltourv2.dao.FirebaseDatabaseHelper;
import com.kitusais.akiltourv2.model.News;
import com.kitusais.akiltourv2.model.NewsComment;

import java.util.ArrayList;
import java.util.List;

public class NewsController {

    private ArrayList<News> news = new ArrayList<News>();

    public ArrayList<News> getAllNews() {
        // if dans groupe

        new FirebaseDatabaseHelper().readNews(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded() {
            }

            @Override
            public void DataIsInserted(List<News> news, List<String> keys) {

//                new RecyclerView_Config().setConfig(mRecyclerView,ResultGame.this,
//                        joueurs, keys);
//                if(countJoueurs < joueurs.size()){
//                    countJoueurs = joueurs.size();
//                    resultGagnant = GameControler.gagnant(joueurs, resultTW);
//
//                    resultTW.setText(resultTW.getText()+"\n avec "+countJoueurs+" joueurs");
//                }
            }

            @Override
            public void DataCommentIsInserted(List<NewsComment> commentsTab, List<String> keys) {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

//        quitResultBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                retourMenu();
//            }
//        });
        Log.i("NewsController.java","news.size() : "+news.size());
        return this.news;
    }
}
