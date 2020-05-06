package com.kitusais.akiltourv2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import com.kitusais.akiltourv2.controler.GetGameDAO;
import com.kitusais.akiltourv2.controler.LoginSigninControler;
import com.kitusais.akiltourv2.controler.SecurityControler;
import com.kitusais.akiltourv2.model.Player;
import com.kitusais.akiltourv2.ui.accueil.GalleryFragment;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static String baseUrl ="http://192.168.1.21/up2net/";
    public static boolean alreadyImported = false;
    public static Player authPlayer;
    public static int numRandomGame = 0;
    public static String previousFragment = null;
    private Context context;
    private AlertDialog alertDialog;
    private boolean success = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        GetGameDAO getGameDAO  =new GetGameDAO(context);
        getGameDAO.execute("random");
        Intent i = getIntent();
        if("usedPseudo".equals(i.getStringExtra("error"))){
            Toast.makeText(context,"pseudo déjà utilisé",Toast.LENGTH_LONG).show();
        }
        if(authPlayer != null ){
            Intent i1 = new Intent(context, MainAppActivity.class);
            context.startActivity(i1);
            finish();
        }
        Button loginBtn = findViewById(R.id.loginSigninLoginBtn);
        final EditText usernameEt = findViewById(R.id.loginSigninPseudoEt);
        final EditText passwordEt = findViewById(R.id.loginSigninPassEt);

        TextView signInLink = findViewById(R.id.signinLink);
        String str = "Pas encore inscrit ? Enregistrez-vous :)";
        SpannableString ss = new SpannableString(str);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
//                Toast.makeText(LoginSignin.this,"tata",Toast.LENGTH_LONG).show();
                final AlertDialog alertDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                View showView = LayoutInflater.from(context).inflate(R.layout.popup_signin_layout, null);
                final TextView pass1 = showView.findViewById(R.id.SignInPopUpPassEt);
                final TextView pass2 = showView.findViewById(R.id.SignInPopUpPassEt2);
                final TextView pseudo = showView.findViewById(R.id.SignInPopUpPseudoEt);
                builder.setView(showView);
                alertDialog = builder.create();
                alertDialog.show();

                Button signinButton = showView.findViewById(R.id.SignInBtn);
                signinButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<String> userEntry = new ArrayList<>();
                        userEntry.add(SecurityControler.firstToUpper(pseudo.getText().toString()));
                        userEntry.add(pass1.getText().toString());
                        userEntry.add(pass2.getText().toString());
                        int[] userEntryLength = {20,20,20};
                        if(SecurityControler.securityMain(context,userEntry, userEntryLength) && SecurityControler.checkPassStrengh(context, userEntry.get(1))) {
                            LoginSigninControler.passCheck(context, userEntry.get(0), userEntry.get(1), userEntry.get(2));
                            //alertDialog.dismiss();
                            //finish();
                        }
//                        Log.i("--- MainActivity ---","Bonjour "+authPlayer.getPseudo());

//                        alertDialog.dismiss();
//                        sayHello();
                    }
                });
            }
        };

        ss.setSpan(clickableSpan,0,str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signInLink.setText(ss);
        signInLink.setMovementMethod(LinkMovementMethod.getInstance());

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> userEntry = new ArrayList<>();
                userEntry.add(usernameEt.getText().toString());
                userEntry.add(passwordEt.getText().toString());
                int[] userEntryLength = {20,20};
                if(SecurityControler.securityMain(context, userEntry, userEntryLength)){
                    LoginSigninControler.logInCheck(context, userEntry.get(0), userEntry.get(1));
                }

//                alertDialog.dismiss();
//                Intent intent = new Intent(context, MainAppActivity.class);
//                startActivity(intent);

                finish();
            }
        });
    }


    public void login(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        View showView = LayoutInflater.from(context).inflate(R.layout.popup_login_signin, null);
//        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
//        eventListTitle = showView.findViewById(R.id.eventsListTitle);
        Button loginBtn = showView.findViewById(R.id.loginSigninLoginBtn);
        final EditText usernameEt = showView.findViewById(R.id.loginSigninPseudoEt);
        final EditText passwordEt = showView.findViewById(R.id.loginSigninPassEt);
        builder.setView(showView);
        alertDialog = builder.create();
        alertDialog.show();

        TextView signInLink = showView.findViewById(R.id.signinLink);
        String str = "Pas encore inscrit ? Enregistrez-vous :)";
        SpannableString ss = new SpannableString(str);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
//                Toast.makeText(LoginSignin.this,"tata",Toast.LENGTH_LONG).show();
                final AlertDialog alertDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                View showView = LayoutInflater.from(context).inflate(R.layout.popup_signin_layout, null);
                final TextView pass1 = showView.findViewById(R.id.SignInPopUpPassEt);
                final TextView pass2 = showView.findViewById(R.id.SignInPopUpPassEt2);
                final TextView pseudo = showView.findViewById(R.id.SignInPopUpPseudoEt);
                builder.setView(showView);
                alertDialog = builder.create();
                alertDialog.show();

                Button signinButton = showView.findViewById(R.id.SignInBtn);
                signinButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<String> userEntry = new ArrayList<>();
                        userEntry.add(SecurityControler.firstToUpper(pseudo.getText().toString()));
                        userEntry.add(pass1.getText().toString());
                        userEntry.add(pass2.getText().toString());
                        int[] userEntryLength = {20,20,20};
                        if(SecurityControler.securityMain(context,userEntry, userEntryLength)) {
                            LoginSigninControler.passCheck(context, userEntry.get(0), userEntry.get(1), userEntry.get(2));
                        }
                        Log.i("--- MainActivity ---","Bonjour "+authPlayer.getPseudo());
                        GalleryFragment.textView.setText("Bonjour "+authPlayer.getPseudo());
                        Fragment frg = null;
//                        frg = getSupportFragmentManager().findFragmentByTag();
                        frg = getSupportFragmentManager().findFragmentById(R.id.nav_view);
                        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.detach(frg);
                        ft.attach(frg);
                        ft.commit();
//
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                            fragmentManager.beginTransaction().detach(this).commitNow();
//                            fragmentManager.beginTransaction().attach(this).commitNow();
//                        } else {
//                            fragmentManager.beginTransaction().detach(this).attach(this).commit();
//                        }

                        alertDialog.dismiss();
                        sayHello();
                    }
                });
            }
        };

        ss.setSpan(clickableSpan,0,str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signInLink.setText(ss);
        signInLink.setMovementMethod(LinkMovementMethod.getInstance());

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> userEntry = new ArrayList<>();
                userEntry.add(usernameEt.getText().toString());
                userEntry.add(passwordEt.getText().toString());
                int[] userEntryLength = {20,20};
                if(SecurityControler.securityMain(context, userEntry, userEntryLength)){
                    LoginSigninControler.logInCheck(context, userEntry.get(0), userEntry.get(1));
                }
                alertDialog.dismiss();
            }
        });
    }

    public void sayHello(){

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_view, new GalleryFragment());
        ft.commit();
    }


}
