package com.kitusais.akiltourv2.ui.Calendrier;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.kitusais.akiltourv2.R;
import com.kitusais.akiltourv2.controler.CalendarController;
import com.kitusais.akiltourv2.controler.EventRecyclerAdapter;
import com.kitusais.akiltourv2.dao.GetRdvDao;
import com.kitusais.akiltourv2.dao.InsertRdvDao;
import com.kitusais.akiltourv2.controler.SecurityControler;
import com.kitusais.akiltourv2.model.ImportedEvent;
//import com.kitusais.akiltourv2.model.ImportedEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.kitusais.akiltourv2.MainActivity.authPlayer;
import static com.kitusais.akiltourv2.dao.GetRdvDao.listEvents;
import static com.kitusais.akiltourv2.dao.GetRdvDao.listImportedEvents;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView eventListTitle;
    public static AlertDialog alertDialog;
    public static AlertDialog alertDialogAddEvent;
    public static AlertDialog alertDialogSelectHour;
    public static ImageView imageSupr, imageModify;
    CompactCalendarView compactCalendar ;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());
    public static Context contextCalendar;
    final Locale lang = Locale.FRENCH;
    private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, final Bundle savedInstanceState) {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        contextCalendar = getContext();

        compactCalendar= root.findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        textView = root.findViewById(R.id.text_home);

        compactCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        String string = new SimpleDateFormat(

                "MMMM",lang).format(compactCalendar.getFirstDayOfCurrentMonth()).substring(0,1).toUpperCase()+
                new SimpleDateFormat("MMMM yyyy",lang).format(compactCalendar.getFirstDayOfCurrentMonth()).substring(1).toLowerCase();
        textView.setText(string);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        Date date = null;
        try {
            date = sdf.parse("07-02-2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        for (Event event:listEvents){
            compactCalendar.addEvent(event);
        }
        for (ImportedEvent event:listImportedEvents){
            Log.i("CalendarFragement", "listImportedEvent : "+event.toString());
        }

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                View showView = LayoutInflater.from(getContext()).inflate(R.layout.show_events_layout, null);
                eventListTitle = showView.findViewById(R.id.eventsListTitle);
                String strEvenement = "Evenement";
                if (CalendarController.collectEventByDate(dateClicked).size() > 1){
                    strEvenement += "s";
                }
                eventListTitle.setText(strEvenement+" pour le "+(new SimpleDateFormat("EEEE \nd MMMM yyyy ", Locale.FRENCH)).format(dateClicked));
                AlertDialog alertDialog = CalendarController.OnDayClick(getContext(), dateClicked);
                alertDialog.show();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                textView.setText(new SimpleDateFormat("MMMM",lang).format(firstDayOfNewMonth).substring(0,1).toUpperCase()+
                        new SimpleDateFormat("MMMM yyyy",lang).format(firstDayOfNewMonth).substring(1).toLowerCase());
            }
        });
        return root;
    }


}