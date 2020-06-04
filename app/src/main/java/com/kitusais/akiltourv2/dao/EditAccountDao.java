package com.kitusais.akiltourv2.dao;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.kitusais.akiltourv2.MainAppActivity;

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
import java.util.ArrayList;

import static com.kitusais.akiltourv2.MainActivity.alreadyImported;
import static com.kitusais.akiltourv2.MainActivity.baseUrl;
import static com.kitusais.akiltourv2.MainActivity.previousFragment;
import static com.kitusais.akiltourv2.dao.GetRdvDao.listEvents;

public class EditAccountDao  extends AsyncTask<String, Void, String> {
        Context context;
        AlertDialog alertDialog;
        String field;


        public EditAccountDao(Context ctx){
            context = ctx;
        }
        @Override
        protected String doInBackground(String... params) {
//        listEvents.clear();
            String id_user = params[0];
            String value = params[1];
            field = params[2];
            String oldPass = params[3];
            Log.i("EditAccountDao","id_user : "+id_user+", value : "+value+", field : "+field);
            String post_url = baseUrl+"editAccount.php";
            try {
                URL url = new URL(post_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                String post_data = "";
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                Log.i("Insert rdv :", "update detected");
//                Log.i("InsertRdvToDatabase","id_user :"+id_user+" - date :"+date+" - message :"+message+"editDate"+editDate+ " - editeEvent"+editEvent);
                post_data  = URLEncoder.encode("id_user","UTF-8")+"="+URLEncoder.encode(id_user,"UTF-8")+"&"+
                        URLEncoder.encode("field","UTF-8")+"="+URLEncoder.encode(field.toLowerCase(),"UTF-8")+"&"+
                        URLEncoder.encode("valueOld","UTF-8")+"="+URLEncoder.encode(oldPass,"UTF-8")+"&"+
                        URLEncoder.encode("value","UTF-8")+"="+URLEncoder.encode(value,"UTF-8");

                Log.i("EditAccountDao","id_user : "+id_user+", value : "+value+", field : "+field);
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
            return null;
        }



        @Override
        protected void onPreExecute() {
//        alertDialog = new AlertDialog.Builder(context).create();
//        alertDialog.setTitle("Status");
        }

        @Override
        protected void onPostExecute(String resultS) {
            Log.i("PostExec",":::");
            Log.i("EditAccountDao",""+resultS);
//            Log.i("InsertRdvDaoResult",action);
            //if(action.equals("update")){
               ((Activity)context).finish();
            //
        Intent intent = new Intent(context, MainAppActivity.class);
        context.startActivity(intent);


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }



