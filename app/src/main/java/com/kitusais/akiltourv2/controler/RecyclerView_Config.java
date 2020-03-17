package com.kitusais.akiltourv2.controler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kitusais.akiltourv2.R;
import com.kitusais.akiltourv2.model.Player;
//import com.kitusais.poeinantes2019.R;
//import com.kitusais.poeinantes2019.model.Joueur;

import java.util.List;

public class RecyclerView_Config {

    private Context mContext;
    private JoueursAdapter mJouersAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Player> joueurs, List<String> keys){
        mContext = context;
        mJouersAdapter = new JoueursAdapter(joueurs, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mJouersAdapter);
    }

    class JoueurItemView extends RecyclerView.ViewHolder{
        private TextView mPseudo;
        private TextView mPasse;

        private String key;

        public JoueurItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.result_list_item, parent, false));

            mPseudo = itemView.findViewById(R.id.pseudo_txtView);
            mPasse = itemView.findViewById(R.id.passe_txtView);
        }

        public void bind(Player joueur, String key){
            mPseudo.setText(joueur.getPseudo());
            mPasse.setText(""+joueur.getGameTimer());

            this.key = key;
        }
    }

    class JoueursAdapter extends RecyclerView.Adapter<JoueurItemView>{
        private List<Player> mJoueurList;
        private List<String> mKeys;

        public JoueursAdapter(List<Player> mJoueurList, List<String> mKeys) {
            this.mJoueurList = mJoueurList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public JoueurItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new JoueurItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull JoueurItemView holder, int position) {
            holder.bind(mJoueurList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mJoueurList.size();
        }
    }
}
