
package com.kitusais.akiltourv2.ui.news;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.kitusais.akiltourv2.R;
import com.kitusais.akiltourv2.controler.RecyclerView_Config;
import com.kitusais.akiltourv2.dao.FirebaseDatabaseHelper;
import com.kitusais.akiltourv2.model.News;
import com.kitusais.akiltourv2.model.NewsComment;
import com.kitusais.akiltourv2.model.NewsString;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.kitusais.akiltourv2.MainActivity.authPlayer;
import static com.kitusais.akiltourv2.MainAppActivity.database;

public class NewsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private Button valid;
    private EditText newNewsTw;
    private ArrayList<News> news = new ArrayList<News>();
    private NewsViewModel resultatsViewModel;
    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        resultatsViewModel =
                ViewModelProviders.of(this).get(NewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        context = this.getContext();
        //final TextView textView = root.findViewById(R.id.text_share);
        //resultatsViewModel.getText().observe(this, new Observer<String>() {
        //    @Override
        //   public void onChanged(@Nullable String s) {
        //       textView.setText(s);
        //   }
        //});
        mRecyclerView = root.findViewById(R.id.recyclerview_news);
        valid = root.findViewById(R.id.submit);
        newNewsTw = root.findViewById(R.id.new_news_message);
        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = newNewsTw.getText().toString();
                newNewsTw.setText("");
                String id = authPlayer.getPseudo()+(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                NewsString newNews = new NewsString();
                newNews.setInitiator(authPlayer.getPseudo());
                newNews.setMessage(message);
                newNews.setId(id);
                newNews.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                final DatabaseReference dbRef = database.getReference("news");
                dbRef.child(id).setValue(newNews);
//                DatabaseReference postsRef = dbRef.child("posts");

//                DatabaseReference newPostRef = postsRef.push();
//                newPostRef.setValueAsync(new News("gracehop", "Announcing COBOL, a New Programming Language"));

            }
        });
//        if(news.size() == 0){
//
//            for(int i=0; i<3; i++){
//                int rand = 0 + (int)(Math.random() * ((10 - 0) + 1));
//
//                news.add(new News());
//                //Log.i("NewsFragment",""+keys.get(i));
//                news.get(i).setInitiator("suerzefez");
//                news.get(i).setMessage("messssssage !!!");
//                news.get(i).setId(rand);
//            }
//        }
        new FirebaseDatabaseHelper().readNews(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded() {
            }

            @Override
            public void DataIsInserted(List<News> news, List<String> keys) {
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
                new RecyclerView_Config().setConfig(mRecyclerView,context,
                        news, keys);
//                if(countJoueurs < joueurs.size()){
//                    countJoueurs = joueurs.size();
//                    resultGagnant = GameControler.gagnant(joueurs, resultTW);
//
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
        return root;
    }
}