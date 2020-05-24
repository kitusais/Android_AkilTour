package com.kitusais.akiltourv2.controler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kitusais.akiltourv2.R;
import com.kitusais.akiltourv2.dao.InsertRdvDao;
import com.kitusais.akiltourv2.model.ImportedEvent;
import com.kitusais.akiltourv2.view.EditEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.kitusais.akiltourv2.MainActivity.authPlayer;
import static com.kitusais.akiltourv2.ui.Calendrier.HomeFragment.contextCalendar;
import static com.kitusais.akiltourv2.MainActivity.previousFragment;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.MyViewHolder> {

    public Context context;
    ArrayList<ImportedEvent> arrayList;
    private static ImportedEvent event;
    public static ImportedEvent eventEdit;
    public static ImportedEvent clickedEvent;
    public EventRecyclerAdapter(Context context, ArrayList<ImportedEvent> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_events_rowlayout,parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
//        Log.i("hahaaha trouvéééé","dede");
        //final ImportedEvent event = arrayList.get(position);
        event = arrayList.get(position);
        String[] linesEvent = event.getMessage().split("\\\\n");
        holder.currentEvent = event;
        holder.idEvent = event.getId();
        holder.eventName.setText("");
        for(int i=0; i<linesEvent.length; i++){
            Log.i("event line "+i+" ", linesEvent[i]);
            holder.eventName.setText(holder.eventName.getText()+linesEvent[i]+"\n");
        }
////        holder.event.setText(events.getMessage());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        holder.dateTxt.setText("Proposé par "+event.getInitiator()+"\nà "+sdf.format(event.getDate()));
////        holder.time.setText();
        if (event.getInitiator().equals(authPlayer.getPseudo())){
//            Log.i("EventRecyclerAdapter","passage dans la boucle initiator = authplayer");
////            ImageButton image = showView.findViewById(R.id.imgSuppr);
            holder.imageSupr.setClickable(true);
            holder.imageSupr.setImageResource(R.drawable.corb);
            holder.imageSupr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteEvent(event);
//                    HomeFragment.delete();
                }
            });
            holder.imageModify.setClickable(true);
            holder.imageModify.setImageResource(R.drawable.pencil);
            holder.imageModify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("recyclerView", "modify click !!!!!");
                    eventEdit = event;
                    ((Activity)context).finish();
                    Intent intent = new Intent(v.getContext(), EditEvent.class);
                    v.getContext().startActivity(intent);

                }
            });
////            holder.image.setLayoutParams(new FrameLayout.LayoutParams(50, 50));
        }

//        Log.i("events.getDATE()",""+events.getDATE());
//        Log.i("events.getTime()",""+events.getTime());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        int idEvent;
        ImportedEvent currentEvent;
        TextView dateTxt, eventName, time;
        ImageView imageSupr, imageModify;


        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickedEvent = currentEvent;
                    CalendarController.onEventClick(context);

                }
            });
            dateTxt = itemView.findViewById(R.id.eventdate);
            eventName = itemView.findViewById(R.id.eventname);
            time = itemView.findViewById(R.id.eventtime);
            imageSupr = itemView.findViewById(R.id.imgSuppr);
            imageModify = itemView.findViewById(R.id.imgModify);
        }
    }

    public void deleteEvent(ImportedEvent event){
        String date = "";
        previousFragment = "deleteCalendar";
//        try {
//            date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(event.getDATE()));
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(event.getDate());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        date += " "+event.getTime();
//        Message = eventDetailsEt.getText().toString();
//        Log.i("ADD Message - ", authPlayer.getPseudo());
//        Log.i("ADD Message - ", event.getYEAR()+"-"+event.getMONTH()+"-"+event.ge);
//        Log.i("ADD Message - ", Message);
        InsertRdvDao insertRdvToDatabase = new InsertRdvDao(contextCalendar);
        insertRdvToDatabase.execute("delete",authPlayer.getPseudo(),date,event.getMessage());
    }
}
