package com.kitusais.akiltourv2.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kitusais.akiltourv2.R;
import com.kitusais.akiltourv2.controler.EventDetailsController;
import com.kitusais.akiltourv2.dao.SetGetParticipantDao;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.kitusais.akiltourv2.MainActivity.authPlayer;
import static com.kitusais.akiltourv2.controler.EventRecyclerAdapter.clickedEvent;
import static com.kitusais.akiltourv2.dao.SetGetParticipantDao.listEventParticipant;

public class EventDetails extends AppCompatActivity {

    private Context context;
    private String decision;
    private TextView title, tvDecision;
    private Button participer, annuler, valider;
    private EditText message;
    private View showView;
    private ImageView ok, no, maybe;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy", new Locale("FR","fr"));
    private SimpleDateFormat hourFormat = new SimpleDateFormat("HH'h' mm", new Locale("FR","fr"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);


        Intent intent = getIntent();
        //Log.i("EventDetail","flag : "+intent.get);
        context = this;
        participer = findViewById(R.id.eventParticipation);
        title = findViewById(R.id.eventDetailsTitle);
        title.setText("Evenement du "+dateFormat.format(clickedEvent.getDate())+"\nA "+hourFormat.format(clickedEvent.getDate())+"\n"
                        +"Proposé par "+clickedEvent.getInitiator()+"\n"
                        +"Message : "+clickedEvent.getMessage()+"\n"
                        +"Avec "+listEventParticipant.size()+" participants");
        EventDetailsController.constList(context, clickedEvent);

        participer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final AlertDialog alertDialog;
                showView = LayoutInflater.from(context).inflate(R.layout.popup_participer, null);
                ok = showView.findViewById(R.id.popup_participation_ok);
                no = showView.findViewById(R.id.popup_participation_no);
                maybe = showView.findViewById(R.id.popup_participation_peutetre);
                valider = showView.findViewById(R.id.popup_participation_valider);
                annuler = showView.findViewById(R.id.popup_participation_annuler);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       decision = "Oui";
                        tvDecision = showView.findViewById(R.id.popup_participation_decision);
                        tvDecision.setText(decision);
                        valider.setActivated(true);
                    }
                });
                maybe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        decision = "Peut-être";
                        tvDecision = showView.findViewById(R.id.popup_participation_decision);
                        tvDecision.setText(decision);
                        valider.setActivated(true);
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        decision = "Non";
                        tvDecision = showView.findViewById(R.id.popup_participation_decision);
                        tvDecision.setText(decision);
                        valider.setActivated(true);
                    }
                });

                builder.setCancelable(true);
                builder.setView(showView);
                alertDialog = builder.create();
                alertDialog.show();
                valider.setActivated(false);
                annuler.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                    valider.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(valider.isActivated()) {
                                message = showView.findViewById(R.id.popup_participation_comment_edit);
                                String[] params = new String[5];
                                params[0] = "set";
                                params[1] = "" + clickedEvent.getId();
                                params[2] = authPlayer.getPseudo();
                                params[3] = decision;
                                params[4] = message.getText().toString();
                                SetGetParticipantDao setGetRdvPersonFromDatabase = new SetGetParticipantDao(context);
                                setGetRdvPersonFromDatabase.execute(params);
                                ((Activity)context).finish();
                            }
                        }
                    });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("EventDetail"," hahaah priege");
    }
}
