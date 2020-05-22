package com.kitusais.akiltourv2.dao;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import android.app.Fragment;
import android.app.FragmentManager;

import com.github.sundeepk.compactcalendarview.domain.Event;
import com.kitusais.akiltourv2.MainAppActivity;
import com.kitusais.akiltourv2.R;
import com.kitusais.akiltourv2.model.ImportedEvent;
import com.kitusais.akiltourv2.ui.Calendrier.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.kitusais.akiltourv2.MainActivity.alreadyImported;
import static com.kitusais.akiltourv2.MainActivity.baseUrl;
import static com.kitusais.akiltourv2.MainActivity.previousFragment;
import static com.kitusais.akiltourv2.ui.Calendrier.HomeFragment.contextCalendar;

public class GetRdvDao extends AsyncTask<Void, Void, String> {


    public static List<ImportedEvent> listImportedEvents = new ArrayList<>();
    public static List<Event> listEvents = new ArrayList<>();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Context context;
    AlertDialog alertDialog;

    public GetRdvDao(Context ctx){
        context = ctx;
    }

    @Override
    protected String doInBackground(Void... params) {
        listEvents.clear();
        String post_url ="";
        post_url = baseUrl+"getRDVCalendar.php";
        try {
            URL url = new URL(post_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
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

    }

    @Override
    protected void onPostExecute(String resultS) {
        alreadyImported = true;
        ArrayList<String> list = new ArrayList<String>();
        String[] tabStr = new String[6];
        String str ="";
        Log.i("GetRdvDao","lancééééé");
        Log.i("GetRdvResult : ", resultS);
        listEvents.clear();
        listImportedEvents.clear();
        try {
            JSONArray jsonArray = new JSONArray(resultS);
            for (int i = 0; i < jsonArray.length(); i++) {
                tabStr = jsonArray.get(i).toString().split("\"");
//                for(String tt:tabStr){
////                    Log.i("- IMPORT DATA -",tt);
//                }
                for (int j=0; j<tabStr.length; j++){
                    //Log.i("--- import RDV ---","tabStr["+j+"] : "+tabStr[j]);
                }
                String string ="";

                if(!tabStr[8].equals("]")) {
                    if (tabStr[7].endsWith("\\") && tabStr[7].length() > 1) {
                        string = tabStr[7].substring(0, tabStr[7].length() - 1);
//                }else  if (tabStr[7].equals("\\")){
//
                    }
                    if (tabStr.length > 9) {
                        for (int ii = 8; ii < tabStr.length - 1; ii++) {
                            if (tabStr[ii].endsWith("\\") && tabStr[ii].length() > 1) {
                                string += "\"" + tabStr[ii].substring(0, tabStr[ii].length() - 1);
                            } else {
                                string += "\"" + tabStr[ii];
                            }
                        }
                    }
                }else{
                    string=tabStr[7];
                }
                String[] linesEvent = string.split("\\\\n");
                String stringTemp = "";
//                if (linesEvent.length > 1) {
                for (int ii = 0; ii < linesEvent.length-1; ii++) {
                    //Log.i("event line " + ii + " ", linesEvent[ii]);
                    stringTemp += linesEvent[ii] + "\n";
                }
                stringTemp += linesEvent[linesEvent.length-1];
                string = stringTemp;
//                }
                //Log.i("--- import message ---",""+string);
                listImportedEvents.add(new ImportedEvent(Integer.parseInt(tabStr[1]), tabStr[3], sdf.parse(tabStr[5]), string));
                listEvents.add(new Event(Color.GREEN,sdf.parse(tabStr[5]).getTime(), string));
                //Log.i("-- IMPORT DB --", ""+listImportedEvents.get(i).getId());
            }//end for

//            Activity activity = (Activity) context;
//            Intent intent = new Intent(context, MainAppActivity.class);
//            intent.putExtra("previousFragment","calendar");
//            context.startActivity(intent);
//            activity.finish();
            Log.i("GetRdvDao",""+previousFragment);
            if("Calendar".equals(previousFragment)){
                Activity activity = (Activity) context;
                activity.finish();
                //Intent intent = new Intent(context, MainAppActivity.class);
                Intent intent = new Intent(context, MainAppActivity.class);
                context.startActivity(intent);
            //}else if("deleteCalendar".equals(previousFragment)){
             //   Activity activity = (Activity) contextCalendar;
            //    activity.finish();
             //   Intent intent = new Intent(context, MainAppActivity.class);
             //   contextCalendar.startActivity(intent);
            }
//            alertDialog.setMessage(str);
//            alertDialog.show();
//            Intent ii1 = new Intent( context, MainActivity.class );
//            ii1.putExtra("importRdv",true);
//            ii1.putStringArrayListExtra("listRdv",list);
//            ii1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(ii1);

//            CustomCalendarView customCalendarView = new CustomCalendarView(context);
//            customCalendarView.

        } catch (JSONException e) {
            Log.i("errror","UNE ERREURE EST SRUVENUE");
//            alertDialog.setMessage("err");
//            e.printStackTrace();
//            alertDialog.show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
