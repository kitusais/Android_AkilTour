package com.kitusais.akiltourv2.controler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.kitusais.akiltourv2.R;
import com.kitusais.akiltourv2.dao.InsertRdvDao;
import com.kitusais.akiltourv2.dao.SetGetParticipantDao;
import com.kitusais.akiltourv2.model.ImportedEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.kitusais.akiltourv2.MainActivity.authPlayer;
import static com.kitusais.akiltourv2.controler.EventRecyclerAdapter.clickedEvent;
import static com.kitusais.akiltourv2.dao.GetRdvDao.listImportedEvents;

public class CalendarController {

    //@Todo si on va pour modifier un event et qu'on fait retour on quitte l'appli
    private static AlertDialog alertDialogSelectHour;
    private static AlertDialog alertDialog;
    private static Date day;
    private static int idEventClicked;

    public static Date getDateClicked(){
        return day;
    }

    public static int getIdEventClicked(){
        return idEventClicked;
    }


    public static AlertDialog OnDayClick(final Context context, Date argDay){

        day = argDay;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        View showView = LayoutInflater.from(context).inflate(R.layout.show_events_layout, null);
        RecyclerView recyclerView = showView.findViewById(R.id.EventsRdv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(showView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        EventRecyclerAdapter eventRecyclerAdapter = new EventRecyclerAdapter(showView.getContext()
                ,collectEventByDate(day));
        recyclerView.setAdapter(eventRecyclerAdapter);
        eventRecyclerAdapter.notifyDataSetChanged();
        builder.setView(showView);

        alertDialog = builder.create();
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"Créer nouvel un événement", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                final View addView = LayoutInflater.from(context).inflate(R.layout.add_newevent_layout, null);
                final EditText eventName = addView.findViewById(R.id.event_name);
                final TextView eventTime =  addView.findViewById(R.id.eventtime);
                final ImageButton setTime = addView.findViewById(R.id.seteventtime);
                eventTime.setText((new SimpleDateFormat("HH:mm")).format(new Date())+":00");
                Button addEvent = addView.findViewById(R.id.addevent);
                setTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setCancelable(true);
                        final View addViewSelectHour = LayoutInflater.from(context).inflate(R.layout.pick_hour, null);
                        Button addHourBtn = addViewSelectHour.findViewById(R.id.selectHourBtn);
                        final TimePicker timePicker = addViewSelectHour.findViewById(R.id.timePicker);
                        timePicker.setIs24HourView(true);
                        builder.setView(addViewSelectHour);
                        alertDialogSelectHour = builder.create();
                        alertDialogSelectHour.show();
                        addHourBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                eventTime.setText(String.format("%02d:%02d:00", timePicker.getHour(), timePicker.getMinute()));
                                alertDialogSelectHour.dismiss();
                            }
                        });
                    }
                });

                builder.setView(addView);
                alertDialog = builder.create();
                alertDialog.show();
                addEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SimpleDateFormat sdf0 = new SimpleDateFormat("dd-MM-yy");
                        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
                        Date addedDate = null;
                        try {
                            addedDate = sdf2.parse(sdf0.format(day)+" "+eventTime.getText());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        List<String> userEntry = new ArrayList<>();
                        userEntry.add(eventName.getText().toString());
                        int[] userEntryLength = {200};
                        if(SecurityControler.securityMain(context, userEntry, userEntryLength)) {
                            saveEvent(context, userEntry.get(0), addedDate);
                        }
                        alertDialog.dismiss();
                    }
                });

            }
        });
        return alertDialog;
    }

    public static ArrayList<ImportedEvent> collectEventByDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
        ArrayList<ImportedEvent> arrayList = new ArrayList<>();
        for(ImportedEvent ev:listImportedEvents){
            if (sdf.format(ev.getDate()).equals(sdf.format(date))){
                ImportedEvent events = new ImportedEvent(ev.getId(), ev.getInitiator(),ev.getDate(),ev.getMessage());
                arrayList.add(events);
            }
        }

        return arrayList;
    }

    public static void onEventClick(Context context){
        String[] params = new String[2];
        params[0] = "get";
        params[1] = ""+clickedEvent.getId();
        SetGetParticipantDao setGetRdvPersonFromDatabase = new SetGetParticipantDao(context);
        setGetRdvPersonFromDatabase.execute(params);
        //((Activity)context).finish();
    }

    public static void saveEvent(Context context, String event, Date date){
        // TODO save sur mysql
        String[] params = new String[4];
        params[0]="create";
        params[1] = authPlayer.getPseudo();
        SimpleDateFormat eventDateTimeFormatSql = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.FRENCH);
        params[2] = eventDateTimeFormatSql.format(date)+":00";

        params[3] = event;
        InsertRdvDao insertRdvDao = new InsertRdvDao(context);
        insertRdvDao.execute(params);
    }
}
