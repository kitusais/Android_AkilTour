package com.kitusais.akiltourv2.controler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.kitusais.akiltourv2.MainActivity;
import com.kitusais.akiltourv2.dao.LoginSigninDao;

import static com.kitusais.akiltourv2.MainActivity.authPlayer;

public class LoginSigninControler {

    public static  void logInCheck(Context context, String login, String pass){
        if(login.equals("")){
            Toast.makeText(context, "Identifiants incorects", Toast.LENGTH_LONG).show();
        }else {
            login(context, login, pass);
        }
    }


    public static void logOut(Context context){
        authPlayer = null;
        ((Activity)context).finish();
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);

    }
    public static void passCheck(Context context, String login, String pass1, String pass2){
        if (!pass1.equals(pass2)){
            Toast.makeText(context, "Les mots de passes ne correspondent pas", Toast.LENGTH_LONG).show();
        }else if(pass1.equals("")){
            Toast.makeText(context, "Vous devez entrer un mot de passe", Toast.LENGTH_LONG).show();
        }else {
            signin(context, login, pass1);
            ((Activity)context).finish();
        }
    }

    public static void login(Context context, String username, String password){
        String type = "post";

        LoginSigninDao loginSigninDao = new LoginSigninDao(context);
        loginSigninDao.execute(type, username, password, "login");
//        finish();
    }

    public static void signin(Context context, String username, String password){
        String type = "post";

        LoginSigninDao loginSigninDao = new LoginSigninDao(context);
        loginSigninDao.execute(type, username, password, "signin");
//        finish();

    }
}
