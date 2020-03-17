package com.kitusais.akiltourv2.controler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kitusais.akiltourv2.MainAppActivity;
import com.kitusais.akiltourv2.R;
import com.kitusais.akiltourv2.ui.miniJeux.SlideshowFragment;

import java.util.Date;

public class GameDoubleButtonControler {

    private static Button button1;
    private static Button button2;
    private static TextView title;
    private static boolean clickButton1 = false;
    //    private boolean start = false;
//    private Context context = this;
    private static long startime;
    private static Handler handler;
    private static Runnable task;
    public static boolean finished = false;

    public static void startGame(final Context context, final View view) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_game_double_button);

        startime = new Date().getTime();
        button1 = (Button) view.findViewById(R.id.gameDoubleButton1);
        button2 = view.findViewById(R.id.gameDoubleButton2);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!clickButton1) {
                    clickButton1 = true;
                    int x = (int) (Math.random() * 300);
                    int y = (int) (Math.random() * 600);
                    button2.setX(x);
                    button2.setY(y);
                    Log.i("oauis c'est la boucle", "" + clickButton1);
                    View view0 = view;
                    change(view0, context);
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Vous devez clicker en premier sur le boutton 1",Toast.LENGTH_LONG).show();
            }
        });

    }

    public static void change(final View view, final Context context){

//        final Handler handler = new Handler();
        handler = new Handler();
//        Runnable task = new Runnable() {
        task = new Runnable() {
            @Override
            public void run() {

                Log.i("GameDoubleButtonControler", "oauis c'est la boucle du handler GGGGGGG");
                int x =(int)(Math.random()*300);
                int y = (int)(Math.random()*600);
                button2.setX(x);
                button2.setY(y);
                handler.postDelayed(this, 200);
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        win(view, clickButton1, context);
                    }
                });
            }
        };

        handler.post(task);

    }

    //TODO: Lorsqu'on click plusieurs fois sur le bouton numero 2 alors plusieurs page de résultat se chargent et avec la bonton
    // navBack -> resultats > acceuil > resultat > acceuil > close app
    public static void win(View view, boolean click1, Context context){
        if(!finished) {
            title = view.findViewById(R.id.gameDoubleButtonTitle);
            if (click1) {
                finished = true;
                title.setText(":)  GG tu as gagné  :)");
                GameControler.finishGame(startime);

//            this.finish();
                handler.removeCallbacks(task);

//            FragmentTransaction transaction = context.getResources();
                Activity act = (Activity) context;
                act.finish();
                Intent i = new Intent(context, MainAppActivity.class);
                context.startActivity(i);
////            transaction = act.getSupportFragmentManager().beginTransaction();
//            transaction =  SlideshowFragment.getFragmentManager().beginTransaction();
//            transaction.replace(R.id.nav_host_fragment, someFragment);
////        transaction.addToBackStack(null);
//            transaction.commit();
            }
        }
    }
}
