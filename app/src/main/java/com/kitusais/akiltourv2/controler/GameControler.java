package com.kitusais.akiltourv2.controler;

import android.util.Log;
import android.widget.TextView;

//import com.google.firebase.database.DatabaseReference;
//import com.kitusais.poeinantes2019.model.Joueur;

import com.google.firebase.database.DatabaseReference;
import com.kitusais.akiltourv2.model.Player;

import java.util.Date;
import java.util.List;

import static com.kitusais.akiltourv2.MainActivity.authPlayer;
import static com.kitusais.akiltourv2.MainAppActivity.database;
//import static com.kitusais.akiltourv2.MainAppActivity.database;
//import static com.kitusais.poeinantes2019.model.BackgroundWorker.authPlayer;
//import static com.kitusais.poeinantes2019.view.MainActivity.database;
//import static com.kitusais.poeinantes2019.view.ResultGame.resultGagnant;

public class GameControler {

    public static void finishGame(long startTime){
        long endTime = new Date().getTime();

        authPlayer.setGameTimer(endTime-startTime);
        final DatabaseReference dbRef = database.getReference("joueurs");
        dbRef.child(authPlayer.getPseudo()).setValue(authPlayer);
    }

    public static String convertTime(long time){
        String timeStr = "";

        return timeStr;
    }

    public static String gagnant(List<Player> joueurs, TextView view){
        Log.i("--GameControler--","************************************ debut fonction ************************************");
        String str = "Au tour de ";
        String strGagnant = "";
        int i=0;
        boolean gagnant = false;
//        while(!gagnant) {
        Log.i("--GameControler--","avant boucle : "+i);
        Log.i("--GameControler--","authPlayer passe : "+authPlayer.getPasse());
        for (i = 0; i < joueurs.size(); i++) {

            Log.i("--GameControler--","boucle : "+i);
            Log.i("--GameControler--","joueur i passe : "+joueurs.get(i).getPasse());
            Log.i("-- Check WINNER --", ""+gagnant);
            if (!joueurs.get(i).getPasse()) {
                strGagnant = joueurs.get(i).getPseudo();
//                    joueurs.get(i).setPasse(true);
                if(joueurs.get(i).getPseudo().equals(authPlayer.getPseudo())){
//                        authPlayer.setPasse(true);
                }
                gagnant = true;
                Log.i("-- Check WINNER --", ""+gagnant);
                break;
            }
        }
        Log.i("--GameControler--","pseudo gagnant avant reset : "+strGagnant);

        view.setText(str+strGagnant);
        if (strGagnant.equals("") && joueurs.size() > 0) {
//            if (i == joueurs.size()) {
//                str = "Tout le monde est passé, la liste à été réinitialisée \n Au tour de ";
//                authPlayer.setPasse(false);
//                for (i = 0; i < joueurs.size(); i++) {
//                    joueurs.get(i).setPasse(false);
//                }
            resetPassage(joueurs, view);
        }
//        }

        Log.i("--GameControler--","pseudo gagnant : "+strGagnant);
        Log.i("GameControler", ""+authPlayer.getPasse());
        return strGagnant;
    }
//
    public static void resetPassage(List<Player> joueurs, TextView view){
//            str = "Tout le monde est passé, la liste à été réinitialisée \n Au tour de ";
        boolean reset = true;
        authPlayer.setPasse(false);
        for (int i = 0; i < joueurs.size(); i++) {
            joueurs.get(i).setPasse(false);
        }
        gagnant(joueurs, view);
    }

    public static void setPasse(String str){
        if(authPlayer.getPseudo().equals(str)){
            authPlayer.setPasse(true);
        }
    }
}
