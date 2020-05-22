package com.kitusais.akiltourv2.dao;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kitusais.akiltourv2.model.Player;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private List<Player> joueurs = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded();
        void DataIsInserted(List<Player> joueurs, List<String> keys);
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("joueurs");
    }

    public void readJoueurs(final DataStatus dataStatus){
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                joueurs.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Player joueur = keyNode.getValue(Player.class);
                    if(joueur.getGameTimer() != 0) {
                        int i=0;
                        for(i=0; i<joueurs.size(); i++){
                            if(joueur.getGameTimer() > joueurs.get(i).getGameTimer()){
                                break;
                            }
                        }

                        joueurs.add(i,joueur);
                    }
                }
                dataStatus.DataIsInserted(joueurs, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

