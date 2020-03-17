package com.kitusais.akiltourv2.controler;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class SecurityControler {

    private static String[] list = new String[]{";","select","drop","update","delete"};

    public static boolean securityMain(Context context, List<String> strings, int[] length){
        boolean check = false;
        if(!checkLength(context, strings, length)){
            return check;
        }else if (!checkChar(context, strings)){
            return check;
        }else {
            check = true;
        }
        return  check;
    }
    public static boolean checkChar(Context context, List<String> str){
        boolean check = true;
        int i =0;
        Log.i("SecurityControler",""+str.size());
        for (i=0; i<str.size(); i++) {
            Log.i("SecurityControler",""+str.get(i).indexOf(';'));
            for(int j=0; j<list.length; j++) {
                if (str.get(i).toLowerCase().indexOf(list[j]) < 0) {
                    check = true;
                } else {
                    check = false;
                    Toast.makeText(context, "désolé vous avez utilisé un caractère interdit par mesure de sécurité", Toast.LENGTH_LONG).show();
                    break;
                }
            }
            if(!check){break;}
        }
        for(String str0:str){
//            if (str0.indexOf())
        }
        Log.i("SecurityControler",""+check+" | i: "+i);
        return check;
    }

    public static String checkQuote(String str){
        String[] arr = str.split("\"");
        String str2 ="";
        str2 += arr[0];
        for(int i=1; i<arr.length; i++){
//            Log.i("TESSSSSST : ",str0);
            str2 += "\""+arr[i];
        }
        return str2;
    }


    public static boolean checkLength(Context context, List<String> strings, int[] length){
        boolean check = false;
        for(int i=0; i<strings.size(); i++){
            strings.set(i,strings.get(i).trim());
            if(strings.get(i).length() > length[i]){
                check = false;
                Toast.makeText(context, "désolé la taille de votre entrée : "+strings.get(i)+"\ndoit être inferieur à "+length[i]+" caractère", Toast.LENGTH_LONG).show();
                break;
            }else{
                check = true;
            }
        }
        return check;
    }

    public static String firstToUpper(String string){
        string = string.substring(0,1).toUpperCase()+string.substring(1,string.length());
        return string;
    }
}
