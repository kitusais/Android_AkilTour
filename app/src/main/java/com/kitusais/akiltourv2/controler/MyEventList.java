package com.kitusais.akiltourv2.controler;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.kitusais.akiltourv2.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.kitusais.akiltourv2.MainActivity.authPlayer;
import static com.kitusais.akiltourv2.dao.SetGetParticipantDao.listEventParticipant;

public class MyEventList  extends ArrayAdapter<String> {
    public static int nbParticipant;
    private final Activity context;
    private final ArrayList<String> initiator;
    private final ArrayList<String> decisions;
    private final ArrayList<String> message;
    private final ArrayList<Date> date;
    private Boolean currentPlayerParticip;
    private Switch eventDetailSwitch;
    //private ImageView imageView;
    private SimpleDateFormat dateFormatSql = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());


    //        public MyListAdapter(Activity context, ArrayList<String> maintitle, ArrayList<Boolean> subtitle, Integer[] message) {
//            super(context, R.layout.mylist, maintitle);
//            // TODO Auto-generated constructor stub
//
//            this.context=context;
//            this.maintitle=maintitle;
//            this.subtitle=subtitle;
//            this.message=message;
//
//        }
    public MyEventList(Activity context, ArrayList<String> initiator, ArrayList<String> message, ArrayList<Date> date, ArrayList<String> decisions) {
//            super(context, R.layout.mylist);
        super(context, R.layout.mylist, message);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.initiator = initiator;
        this.message = message;
        this.date = date;
        this.decisions = decisions;
//        this.buttons=buttons;
        nbParticipant = 0;

    }

//    public MyListAdapter() {
//    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.mylist, null, true);
        nbParticipant = 0;

        ImageView imageView = rowView.findViewById(R.id.eventDetailsIcon);
        //imageView.setImageResource(R.drawable.out);
        currentPlayerParticip = false;
        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        TextView messageText = (TextView) rowView.findViewById(R.id.eventDetailsTw);

        for (int i = 0; i < listEventParticipant.size(); i++) {
            if (authPlayer.getPseudo().equals(listEventParticipant.get(i).getUsername()) && message.get(position).equals(listEventParticipant.get(i).getMessage())) {
                currentPlayerParticip = true;
            }
            if (CalendarController.getDateClicked().equals(dateFormatSql.format(listEventParticipant.get(i).getDate())) && message.get(position).equals(listEventParticipant.get(i).getMessage())) {
                nbParticipant++;

            }
        }

        Log.i("MyEventList","decision : "+decisions.get(position));
        if ("Oui".equals(decisions.get(position))) {
            imageView.setImageResource(R.drawable.in);
        }else if("Non".equals(decisions.get(position))){
            imageView.setImageResource(R.drawable.out);
        }else if("Peut-être".equals(decisions.get(position))){
            imageView.setImageResource(R.drawable.interrogation);
        }
        String strParticipant = " participant";
        if (nbParticipant > 1) {
            strParticipant = strParticipant + "s";
        }
        titleText.setText(initiator.get(position));
        messageText.setText("Commentaire : "+message.get(position));


        return rowView;

    }

    ;
}
