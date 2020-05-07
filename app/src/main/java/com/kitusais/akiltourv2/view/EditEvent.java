package com.kitusais.akiltourv2.view;

//
// après grand remplacement
//
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.kitusais.akiltourv2.R;
import com.kitusais.akiltourv2.controler.InsertRdvDao;
import com.kitusais.akiltourv2.controler.SecurityControler;
import com.kitusais.akiltourv2.model.ImportedEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.kitusais.akiltourv2.MainActivity.authPlayer;
import static com.kitusais.akiltourv2.controler.EventRecyclerAdapter.eventEdit;

public class EditEvent extends AppCompatActivity {

    private TextView eventBeforeEdit, editDateET, editHourET;
    private EditText editEventET;
    private Button editBtn;
    private TextView editDateBtn, editTimeBtn;
    private List<String> userEntry = new ArrayList<>();
    private String editEventDate, editEventTime;
    //private String editEventTime;
    private String eventEditDate;
    private Context contextCalendar0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        contextCalendar0 = this;
        eventEditDate = new SimpleDateFormat("dd/MM/yyyy").format(eventEdit.getDate());
        editEventTime = new SimpleDateFormat("HH:mm").format(eventEdit.getDate());
        Log.i("update on creattttte",eventEdit.getDate().toString());
        Log.i("update on creattttte",editEventTime);
        eventBeforeEdit = findViewById(R.id.eventBeforeEdit);
        editDateET = findViewById(R.id.modifDateET);
        editDateET.setText(eventEditDate);
        editHourET = findViewById(R.id.modifHourET);
        editHourET.setText(editEventTime);
        editEventET = findViewById(R.id.modifEventET);
        editBtn = findViewById(R.id.modifBtn);
        editDateBtn = findViewById(R.id.modifDateBtn);
        editTimeBtn = findViewById(R.id.modifHourBtn);
        SpannableString ss = new SpannableString(editDateBtn.getText());
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                pickDate();
            }
        };
        ss.setSpan(clickableSpan,0,editDateBtn.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editDateBtn.setText(ss);
        editDateBtn.setMovementMethod(LinkMovementMethod.getInstance());

        SpannableString ss2 = new SpannableString(editTimeBtn.getText());
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                pickTime();
            }
        };
        ss2.setSpan(clickableSpan2,0,editTimeBtn.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editTimeBtn.setText(ss2);
        editTimeBtn.setMovementMethod(LinkMovementMethod.getInstance());
        editEventET.setText(eventEdit.getMessage());

        String date ="";
        date = (new SimpleDateFormat("EEEE d MMMM yyyy ", Locale.FRENCH)).format(eventEdit.getDate());
        eventBeforeEdit.setText(eventEdit.getMessage()+"\nle "+date+" à "+editEventTime);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEntry.clear();
                userEntry.add(editEventET.getText().toString().trim());
                int[] userEntryLength = {200};
                Log.i("etstEdit",editEventDate+" "+editEventTime);
                if(SecurityControler.securityMain(EditEvent.this,userEntry, userEntryLength)) {
                    updateEvent(editEventDate, editEventTime, userEntry.get(0));
                }
            }
        });
    }


    public void updateEvent(String editDate, String editHour, String editEvent){
        String initDate = "";
        Log.i("updaaaaaaaaaaaate Event"," - "+editHour+" - ");
        try {
            initDate = (new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH)).format(eventEdit.getDate());
            if(editDate != null) {
                editDate = (new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH)).format((new SimpleDateFormat("dd/MM/yyyy")).parse(editDate));
            }
        } catch (ParseException e) {

            Log.i("function updateEvent","error on date");
            e.printStackTrace();
        }
        if (editDate == null){
            editDate = (new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH)).format(eventEdit.getDate());
            //editDate = (new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH)).format(eventEdit.getDate());
        }
        if (editHour == null || editHour.equals("")){
            Log.i("update Event", "editHour est null !!!!!!!");
            editHour = editEventTime;
            Log.i("update Event", "eventEdit.getTime() : "+editEventTime);
            Log.i("update Event", "editHour : "+editHour);
        }
        if (editEvent == null){
            editEvent = eventEdit.getMessage();
        }
        String editDateTime ="";
//        try {
//            editDateTime = (new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH)).format((new SimpleDateFormat("dd/MM/yyyy")).parse(editDate));
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        initDate += " "+new SimpleDateFormat("HH:mm").format(eventEdit.getDate())+":00";
        editDateTime = editDate;
        editDateTime += " "+editHour;
        Log.i("Click Button"," --- "+editDateTime+" - "+editEvent);
        InsertRdvDao insertRdvDao = new InsertRdvDao(contextCalendar0);
        insertRdvDao.execute("update",authPlayer.getPseudo(),initDate,eventEdit.getMessage(),editDateTime,editEvent);
        finish();

    }

    public void pickDate(){
        final AlertDialog alertDialog;

        AlertDialog.Builder builder = new AlertDialog.Builder(EditEvent.this);
        builder.setCancelable(true);
        final View showView = LayoutInflater.from(EditEvent.this).inflate(R.layout.edit_date, null);


        builder.setView(showView);
        alertDialog = builder.create();
        alertDialog.show();

        CalendarView cal = showView.findViewById(R.id.calendarView);

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView arg0, int year, int month,
                                            int date) {
                Log.i("nfjenivernvjerniver",date+month+year+"");
                editEventDate = date+"/"+(month+1)+"/"+year;
                Toast.makeText(getApplicationContext(),editEventDate,Toast.LENGTH_LONG).show();
                editDateET.setText(editEventDate);
                alertDialog.dismiss();

            }
        });
    }

    public void pickTime(){
        final AlertDialog alertDialog2;

        AlertDialog.Builder builder = new AlertDialog.Builder(EditEvent.this);
        builder.setCancelable(true);
        final View showView = LayoutInflater.from(EditEvent.this).inflate(R.layout.edit_hour, null);


        builder.setView(showView);
        alertDialog2 = builder.create();
        alertDialog2.show();
        final TimePicker timePicker =showView.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        timePicker.setHour(Integer.parseInt(editEventTime.substring(0,2)));
        timePicker.setMinute((Integer.parseInt(editEventTime.substring(3,5))));
        //Log.i("event minutes", editEventTime.substring(6));
        Button valid = showView.findViewById(R.id.modifNewHourBtn);
        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editEventTime = timePicker.getHour()+":"+timePicker.getMinute() ;               //requiert API 23 au lieu de 16
//                editEventTime = "12:30:00";
                editHourET.setText(editEventTime);
                alertDialog2.dismiss();
            }
        });
    }


}

