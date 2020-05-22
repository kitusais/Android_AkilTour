package com.kitusais.akiltourv2.dao;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.kitusais.akiltourv2.MainActivity;
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

import static com.kitusais.akiltourv2.MainActivity.baseUrl;
import static com.kitusais.akiltourv2.MainActivity.numRandomGame;

public class GetGameDAO extends AsyncTask<String, Void, String> {

        private Context context;
        private AlertDialog alertDialog;
        private SharedPreferences preferences;

        public GetGameDAO(Context ctx){
                context = ctx;
                }
        @Override
        protected String doInBackground(String... params) {
                String action = params[0];

                String post_url ="";
                if (action.equals("random")) {
                        post_url = baseUrl+"getRandomGame.php";
                }else if (action.equals("result")){
                        //@TODO A decommenter génère une errure à l'execution
//                        post_url = baseUrl+"getResultGame.php";
                        post_url = baseUrl+"getRandomGame.php";
                }
                try {
                        URL url = new URL(post_url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);
                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                        String post_data  = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"+
//                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
//                        bufferedWriter.write(post_data);
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
                return null;
        }


        @Override
        protected void onPreExecute() {
                alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Status");
        }

        @Override
        protected void onPostExecute(String resultS) {
//                //@TODO à décommenter génère une erreur au demarrage
//                int result = Integer.parseInt(resultS);
//                if(result>0 && result<4){
//                        numRandomGame = result;
//                }
//                Log.i("-- GetGameDAO --", resultS);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
        }
}
