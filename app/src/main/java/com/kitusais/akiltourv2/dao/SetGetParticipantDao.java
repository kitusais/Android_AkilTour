package com.kitusais.akiltourv2.dao;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.kitusais.akiltourv2.MainAppActivity;
import com.kitusais.akiltourv2.model.EventParticipant;
import com.kitusais.akiltourv2.model.ImportedEvent;
import com.kitusais.akiltourv2.view.EventDetails;

import org.json.JSONArray;
import org.json.JSONException;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.kitusais.akiltourv2.MainActivity.baseUrl;
import static java.lang.Integer.parseInt;

public class SetGetParticipantDao extends AsyncTask<String, Void, String> {

    public static ArrayList<EventParticipant> listEventParticipant = new ArrayList<>();
    private String action;
    Context context;
    AlertDialog alertDialog;

    public SetGetParticipantDao(Context ctx){
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        listEventParticipant.clear();
        String post_url = baseUrl+"setGetEventParticipant.php";
        action = params[0];
        String id_event = "";
        String username = "";
        String decision = "";
        String message = "";
        id_event = params[1];
        if (action.equals("set")){
            username = params[2];
            decision = params[3];
            message = params[4];
        }
        try {
            URL url = new URL(post_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data  = URLEncoder.encode("action","UTF-8")+"="+URLEncoder.encode(action,"UTF-8")
                                +"&"+ URLEncoder.encode("id_event","UTF-8")+"="+URLEncoder.encode(id_event,"UTF-8");
            if (action.equals("set")){
                post_data += "&"+URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")
                            +"&"+URLEncoder.encode("decision","UTF-8")+"="+URLEncoder.encode(decision,"UTF-8")
                        +"&"+URLEncoder.encode("message","UTF-8")+"="+URLEncoder.encode(message,"UTF-8");
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
        //alertDialog = new AlertDialog.Builder(context).create();
        //alertDialog.setTitle("Status");
    }

    @Override
    protected void onPostExecute(String resultS) {

        //((Activity)context).getLifecycle().observer();
        //((Activity)context).finish();
        ArrayList<String> list = new ArrayList<String>();
        String str ="";
        Log.i("SetGetParticipantDao", " check resultS : ");
        //resultS = "["+resultS.substring(1, resultS.length()-1).replaceAll("\\[","{").replaceAll("]","}")+"]";
        try {
            JSONArray jsonArray = new JSONArray(resultS);
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.get(i).toString());
                //Log.i("SetGetParticipantDao", i+" check jsonArray : "+jsonArray.get(1));
                String[] tokens = jsonArray.get(i).toString().split("\",\"");
                //Log.i("SetGetParticipantDao", i+" check participant tokens["+i+"] : "+tokens[i]);
                tokens[0] = tokens[0].substring(2, tokens[0].length());
                tokens[(tokens.length) - 1] = tokens[(tokens.length) - 1].substring(0, tokens[(tokens.length) - 1].length() - 2);
                Date date1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tokens[1]);
                Log.i("SetGetRdvFromDatabase",""+tokens[1]);
                //Log.i("setgetrequest :",tokens[(tokens.length) - 1]);
                listEventParticipant.add(new EventParticipant(parseInt(tokens[0]),date1, tokens[2], tokens[3], tokens[4]));
                Log.i("SetGetParticipantDao", i+" check participant usdername : "+listEventParticipant.get(i).getUsername());
            }
        } catch (JSONException e) {
            Log.i("setGetRdvPerson -- :", " Json error ");
            Log.i("setGetRdvPerson -- :", " "+resultS);
            e.printStackTrace();
            //alertDialog.show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i("setGetFromDB","passage dans SetGetFromDB");
        Intent intent;
        if(action.equals("set")) {
            intent = new Intent(context, MainAppActivity.class);
            intent.putExtra( "previousFragment","EventDetail");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }else{
        intent = new Intent(context, EventDetails.class);
        }
        intent.putExtra("EventParticipant",true);
        intent.putParcelableArrayListExtra("listParticipant",listEventParticipant);
        context.startActivity(intent);

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public ArrayList<ImportedEvent> loadRdv(ArrayList<String> list, CompactCalendarView compactCalendar) throws ParseException {

        ArrayList<ImportedEvent> listEvent = new ArrayList<>();
        String strSub="";
        for (int i=0;i<list.size();i++) {
            strSub = list.get(i);
            strSub = strSub.substring(1, strSub.length() - 1) + "\n";
            String[] tokens = strSub.split("\",\"");
            tokens[0] = tokens[0].substring(1,tokens[0].length());
            tokens[3] = tokens[3].substring(0,tokens[3].length()-2);
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(tokens[2]);
            listEvent.add(new ImportedEvent(parseInt(tokens[0]),tokens[1]  ,date,tokens[3]));
            Event ev1 = new Event(Color.GREEN, listEvent.get(i).getDate().getTime());    //
            compactCalendar.addEvent(ev1);
        }
        return listEvent;
    }

}
