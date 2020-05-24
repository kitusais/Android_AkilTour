package com.kitusais.akiltourv2.controler;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.kitusais.akiltourv2.R;
import com.kitusais.akiltourv2.model.EventParticipant;
import com.kitusais.akiltourv2.model.ImportedEvent;
import com.kitusais.akiltourv2.model.Player;

import java.util.ArrayList;
import java.util.Date;

import static com.kitusais.akiltourv2.dao.SetGetParticipantDao.listEventParticipant;

public class EventDetailsController {



    public ArrayList<EventParticipant> getEventParticipant(int id_event, ArrayList<EventParticipant> totalEventParticipants){
        ArrayList<EventParticipant> eventParticipants = new ArrayList<>();

        return eventParticipants;
    }

    public static void participer(Context context){

    }

    public static void constList(Context context, ImportedEvent event){
        Log.i("EventDetailsController","total event size : "+listEventParticipant.size());
        for(int i=0; i<listEventParticipant.size(); i++){
            Log.i("EventDetailsController", "event id : "+listEventParticipant.get(i).getUsername());
        }
        ListView list;
//        int nbParticipant=0;
        ArrayList<String> listEventmessage = new ArrayList<>();
        ArrayList<Date> listEventDate = new ArrayList<>();
        ArrayList<String> listEventCurrentParticipant = new ArrayList<>();
        ArrayList<String> decisions = new ArrayList<>();
        for (int i=0; i<listEventParticipant.size(); i++){
            listEventmessage.add(listEventParticipant.get(i).getMessage());
            listEventDate.add(listEventParticipant.get(i).getDate());
            listEventCurrentParticipant.add(listEventParticipant.get(i).getUsername());
            decisions.add(listEventParticipant.get(i).getDecision());
//            Log.i("Event details  const",""+listEventCurrentDay.get(i).getDate());
        }
        MyEventList adapter=new MyEventList((Activity)context, listEventCurrentParticipant  , listEventmessage, listEventDate, decisions);
//        //list=(ListView)findViewById(R.id.list);
        list= ((Activity)context).findViewById(R.id.list);
        list.setAdapter(adapter);
    }
}
