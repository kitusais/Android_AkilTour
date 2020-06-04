package com.kitusais.akiltourv2.controler;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.kitusais.akiltourv2.R;
import com.kitusais.akiltourv2.model.News;
import com.kitusais.akiltourv2.model.News;
import com.kitusais.akiltourv2.model.NewsComment;
//import com.kitusais.poeinantes2019.R;
//import com.kitusais.poeinantes2019.model.New;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.kitusais.akiltourv2.MainActivity.authPlayer;
import static com.kitusais.akiltourv2.MainAppActivity.database;

public class RecyclerView_Config {

    private Context mContext;
    private NewsAdapter mJouersAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<News> newsList, List<String> keys){
        mContext = context;
        mJouersAdapter = new NewsAdapter(newsList, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mJouersAdapter);
    }

    class NewItemView extends RecyclerView.ViewHolder{
        private News mNews;
        private TextView mPseudo;
        private TextView mMessage;
        private TextView mHour;
        private TextView mCommentSize;
        private String key;

        public NewItemView(final ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.result_list_item, parent, false));
//            itemView.setBackgroundColor(Color.parseColor("#00ff00"));

            mPseudo = itemView.findViewById(R.id.pseudo_txtView);
            mMessage = itemView.findViewById(R.id.message_txtView);
            mHour = itemView.findViewById(R.id.news_hour);
            mCommentSize = itemView.findViewById(R.id.comment_txtView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommentController.showComments(mContext, mNews);
//                    AlertDialog alertDialog;
//                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//                    builder.setCancelable(true);
//                    final View addView = LayoutInflater.from(mContext).inflate(R.layout.news_comment_layout, null);
//
//                    TextView addCommentTile = addView.findViewById(R.id.addCommentTitle);
//                    addCommentTile.setText(addCommentTile.getText().toString()+"\n"+mPseudo.getText()+" - "+mMessage.getText());
//                    Button addBtn = addView.findViewById(R.id.addCommentBtn);
//                    addBtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            EditText addComment =  addView.findViewById(R.id.addCommentEditText);
//                            String comment =addComment.getText().toString();
//                            String id = authPlayer.getPseudo()+(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
//                            NewsComment newsComment = new NewsComment(id,authPlayer.getPseudo(),comment);
//                            final DatabaseReference dbRef = database.getReference("news");
//                            dbRef.child(""+mNews.getInitiator()+" "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(mNews.getDate())).child("comments").child(id).setValue(newsComment);
//                        }
//                    });
//                    builder.setView(addView);
//                    alertDialog = builder.create();
//                    alertDialog.show();

                }
            });
        }

        public void bind(News news, String key, int position){
            if(position%2 == 0){
                itemView.setBackgroundColor(Color.parseColor("#efefef"));
            }
            mNews = news;
            mPseudo.setText(news.getInitiator());
            mMessage.setText(""+news.getMessage());
            mHour.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(news.getDate()));
            if(null != news.getComments()){
                mCommentSize.setText(""+news.getComments().size());
                if (news.getComments().size()>1){
                    mCommentSize.setText(mCommentSize.getText()+" commentaires");
                }else{
                    mCommentSize.setText(mCommentSize.getText()+" commentaire");
                }
            }else {
                mCommentSize.setText("0 commentaire");
            }
            this.key = key;

        }
    }

    class NewsAdapter extends RecyclerView.Adapter<NewItemView>{
        private List<News> mNewList;
        private List<String> mKeys;

        public NewsAdapter(List<News> mNewList, List<String> mKeys) {
            this.mNewList = mNewList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public NewItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new NewItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull NewItemView holder, int position) {
            holder.bind(mNewList.get(position), mKeys.get(position), position);
        }

        @Override
        public int getItemCount() {
            return mNewList.size();
        }
    }
}
