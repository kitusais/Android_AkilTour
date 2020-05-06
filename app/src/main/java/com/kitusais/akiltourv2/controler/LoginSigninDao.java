package com.kitusais.akiltourv2.controler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.kitusais.akiltourv2.MainActivity;
import com.kitusais.akiltourv2.MainAppActivity;
import com.kitusais.akiltourv2.model.Player;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static com.kitusais.akiltourv2.MainActivity.authPlayer;
import static com.kitusais.akiltourv2.MainActivity.baseUrl;

public class LoginSigninDao extends AsyncTask<String, Void, String> {

    private Context context;
    private AlertDialog alertDialog;
    private SharedPreferences preferences;
    Activity mActivity;

    public LoginSigninDao(Context ctx){
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String username = params[1];
        String password = params[2];
        String action = params[3];

        String post_url ="";
        if (action.equals("login")) {
            post_url = baseUrl+"login.php";
        }else if (action.equals("signin")){
            post_url = baseUrl+"signin.php";
        }
        if (type.equals("post")){
            try {
                URL url = new URL(post_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data  = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader =  new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Status");
    }

    @Override
    protected void onPostExecute(String resultS) {
        Log.i("--BackgroundWordker--",resultS);
        if(resultS.equals("usedNickname")){
            Intent i1 = new Intent(context, MainActivity.class);
            i1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            i1.putExtra("error","usedPseudo");
            context.startActivity(i1);

        }else if(resultS.equals("error") || resultS.equals("falsePseudo")){
            Toast.makeText(context,"logins incorrects",Toast.LENGTH_LONG).show();
            Intent i1 = new Intent(context, MainActivity.class);
            context.startActivity(i1);
        }else {
            try {
                JSONObject resultJ = new JSONObject(resultS);
                String[] result = new String[2];
                result[0] = resultJ.getString("id");
                result[1] = resultJ.getString("username");

//                SetLog setLog = new SetLog(context);
//                setLog.execute(result[1], "1");

                authPlayer = new Player(Integer.parseInt(result[0]),result[1]);
//                alertDialog.setMessage("Bonjour " + authPlayer.getPseudo());
//                alertDialog.show();

//                Joueur currentPlayer = new Joueur(true, result[1]);


//            Bundle bundle = new Bundle();
//            bundle.putSerializable("Joueur",currentPlayer);
                Intent i1 = new Intent(context, MainActivity.class);
//                i1.putExtra("Joueur", authPlayer);
//                i1.putExtra("logedIn", true);
                context.startActivity(i1);



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
