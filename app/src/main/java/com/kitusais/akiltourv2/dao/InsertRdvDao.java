package com.kitusais.akiltourv2.dao;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


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


public class InsertRdvDao extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alertDialog;
    String action;


    public InsertRdvDao(Context ctx){
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
//        listEvents.clear();
        Log.i("insertRdvToDatabase : ",""+params.length);
        for(int i=0; i<params.length; i++){
            Log.i("params["+i+"]",""+params[i]);
        }
        action = params[0];
        String username = params[1];
        String date = params[2];
        String message = params[3];

        String post_url = baseUrl+"insertRdv.php";
        try {
            URL url = new URL(post_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            String post_data = "";
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            if (action.equals("update")){
                Log.i("Insert rdv :", "update detected");
                String editDate = params[4];
                String editEvent = params[5];
                Log.i("InsertRdvToDatabase","username :"+username+" - date :"+date+" - message :"+message+"editDate"+editDate+ " - editeEvent"+editEvent);
                post_data  = URLEncoder.encode("action","UTF-8")+"="+URLEncoder.encode(action,"UTF-8")+"&"+
                        URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"+
                        URLEncoder.encode("date","UTF-8")+"="+URLEncoder.encode(date,"UTF-8")+"&"+
                        URLEncoder.encode("message","UTF-8")+"="+URLEncoder.encode(message,"UTF-8")+"&"+
                        URLEncoder.encode("editDate","UTF-8")+"="+URLEncoder.encode(editDate,"UTF-8")+"&"+
                        URLEncoder.encode("editEvent","UTF-8")+"="+URLEncoder.encode(editEvent,"UTF-8");
            }else{
                post_data  = URLEncoder.encode("action","UTF-8")+"="+URLEncoder.encode(action,"UTF-8")+"&"+
                        URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"+
                        URLEncoder.encode("date","UTF-8")+"="+URLEncoder.encode(date,"UTF-8")+"&"+
                        URLEncoder.encode("message","UTF-8")+"="+URLEncoder.encode(message,"UTF-8");
            }
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
        ArrayList<String> list = new ArrayList<String>();
        String str ="";
        Log.i("insertDaoResult",resultS);
        alreadyImported = true;
        listEvents.clear();
        Log.i("InsertRdvDaoResult",action);
        //if(action.equals("update")){
        //    ((Activity)context).finish();
        //}
        previousFragment = "Calendar";
        GetRdvDao getRdvFromDatabase = new GetRdvDao(context);
        getRdvFromDatabase.execute();
//        Intent intent = new Intent(context, Calendar0.class);
//        context.startActivity(intent);


    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

