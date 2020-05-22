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
//    GetRdvDao getRdvFromDatabase = new GetRdvDao(getActivity().getApplicationContext());

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, final Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
//        finished = false;
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
//        getRdvFromDatabase.execute();
        contextCalendar = getContext();
        if(authPlayer == null){
//            login();
        }
//        if (!alreadyImported){
//            GetRdvDao getRdvDao = new GetRdvDao(getActivity().getApplicationContext());
//            getRdvDao.execute();
//
//        }
//        final ActionBar -actionBar = getSupportActionBar();

        compactCalendar= root.findViewById(R.id.compactcalendar_view);
//        compactCalendar.setLocale(TimeZone.getTimeZone("Europe/Paris"), Locale.FRENCH); // requiert API 24 => default 16
        compactCalendar.setUseThreeLetterAbbreviation(true);
//        compactCalendar.setCurrentDate(new Date());

        textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//            }
//        });
        compactCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        Log.i("firstDayOfCurrentmonth : ",""+compactCalendar.getFirstDayOfCurrentMonth().toString());
        String string = new SimpleDateFormat(

                "MMMM",lang).format(compactCalendar.getFirstDayOfCurrentMonth()).substring(0,1).toUpperCase()+
                new SimpleDateFormat("MMMM yyyy",lang).format(compactCalendar.getFirstDayOfCurrentMonth()).substring(1).toLowerCase();
        textView.setText(string);

        // Add event 1 on Sun, 07 Jun 2015 18:20:51 GMT

//        Button test = root.findViewById(R.id.test);
//        test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                getActivity().getSupportFragmentManager().beginTransaction().
//                        remove(getChildFragmentManager().getPrimaryNavigationFragment()).commit();
////                        remove(getActivity().getSupportFragmentManager().findFragmentById()).commit();
//                GalleryFragment test = new GalleryFragment();
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.nav_host_fragment, test, "fragment")
//                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                        .commit();
//
//            }
//        });



        Event ev1 = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        Date date = null;
        try {
            date = sdf.parse("07-02-2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
//        try {
//            ev1 = new Event(Color.GREEN, new SimpleDateFormat("dd/MM/yyyy").parse("05/02/2020").getTime(),
//                    "Some extra data that I want to store.");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        compactCalendar.addEvent(ev1);

        Log.i("CalendarFragment ", "listImportedEvent");
        for (Event event:listEvents){
            compactCalendar.addEvent(event);
        }
        for (ImportedEvent event:listImportedEvents){
            Log.i("CalendarFragement", "listImportedEvent : "+event.toString());
        }
        // Added event 2 GMT: Sun, 07 Jun 2015 19:10:51 GMT
//        Event ev2 = new Event(Color.GREEN, cal.getTimeInMillis());
//        compactCalendar.addEvent(ev2);

        // Query for events on Sun, 07 Jun 2015 GMT.
        // Time is not relevant when querying for events, since events are returned by day.
        // So you can pass in any arbitary DateTime and you will receive all events for that day.
//        List<Event> events = compactCalendar.getEvents(1433701251000L); // can also take a Date object

        // events has size 2 with the 2 events inserted previously
//        Log.d("Log event", "Events: " + events);


        // define a listener to receive callbacks when certain events happen.
        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
//                Calendar dateCal = Calendar.getInstance();
//                dateCal.setTime(dateClicked);
//                dateCal.add(Calendar.DAY_OF_MONTH,1);
//                dateClicked = new Date(dateCal.getTimeInMillis());
//                if(){
//
//                }
                //dayclick(dateClicked);


                View showView = LayoutInflater.from(getContext()).inflate(R.layout.show_events_layout, null);
                eventListTitle = showView.findViewById(R.id.eventsListTitle);
                String strEvenement = "Evenement";
                if (CalendarController.collectEventByDate(dateClicked).size() > 1){
                    strEvenement += "s";
                }
                eventListTitle.setText(strEvenement+" pour le "+(new SimpleDateFormat("EEEE \nd MMMM yyyy ", Locale.FRENCH)).format(dateClicked));
                AlertDialog alertDialog = CalendarController.OnDayClick(getContext(), dateClicked);
                alertDialog.show();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                List<Event> listEvents = compactCalendar.getEvents(dateClicked);
                for(ImportedEvent event:listImportedEvents) {
                    if (sdf.format(event.getDate()).equals(sdf.format(dateClicked))) {
                        Log.d("Day clicked", "Day was clicked: " + dateClicked + "\nwith events " + listEvents);
                    }
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
//                Calendar dateCal = Calendar.getInstance();
//                dateCal.setTime(firstDayOfNewMonth);
//                dateCal.add(Calendar.DAY_OF_MONTH,1);
//                firstDayOfNewMonth = new Date(dateCal.getTimeInMillis());
                Log.d("MonthScroll", "Month was scrolled to: " + firstDayOfNewMonth);
                textView.setText(new SimpleDateFormat("MMMM",lang).format(firstDayOfNewMonth).substring(0,1).toUpperCase()+
                        new SimpleDateFormat("MMMM yyyy",lang).format(firstDayOfNewMonth).substring(1).toLowerCase());
            }
        });
        return root;
    }

    private void collectEventsPerMonth(String Month, String Year){
        GetRdvDao getRdvDao = new GetRdvDao(contextCalendar);
        getRdvDao.execute();
    }

    public void dayclick(final Date day){
        Log.i("CalendarFragment", "day clicked : "+day.toString());
        ArrayList<ImportedEvent> listDayImportedEvent = CalendarController.collectEventByDate(day);
//        for(ImportedEvent event:listDayImportedEvent){
//            Log.i("onDayClick"," listDayImportedEvent :"+event.toString());
//        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        View showView = LayoutInflater.from(getActivity()).inflate(R.layout.show_events_layout, null);
//        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
//        eventListTitle = showView.findViewById(R.id.eventsListTitle);
        eventListTitle = showView.findViewById(R.id.eventsListTitle);
        String strEvenement = "Evenement";
        //if (collectEventByDate(day).size() > 1){
        if (CalendarController.collectEventByDate(day).size() > 1){
            strEvenement += "s";
        }
        eventListTitle.setText(strEvenement+" pour le "+(new SimpleDateFormat("EEEE \nd MMMM yyyy ", Locale.FRENCH)).format(day));
        RecyclerView recyclerView = showView.findViewById(R.id.EventsRdv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(showView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        EventRecyclerAdapter eventRecyclerAdapter = new EventRecyclerAdapter(showView.getContext()
                ,CalendarController.collectEventByDate(day));
        recyclerView.setAdapter(eventRecyclerAdapter);
        eventRecyclerAdapter.notifyDataSetChanged();
        builder.setView(showView);

        alertDialog = builder.create();
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"Créer nouvel un événement", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                final View addView = LayoutInflater.from(getContext()).inflate(R.layout.add_newevent_layout, null);
                final EditText eventName = addView.findViewById(R.id.event_name);
                final TextView eventTime =  addView.findViewById(R.id.eventtime);
                final ImageButton setTime = addView.findViewById(R.id.seteventtime);
                eventTime.setText((new SimpleDateFormat("HH:mm")).format(new Date())+":00");
                Button addEvent = addView.findViewById(R.id.addevent);
                setTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setCancelable(true);
                        final View addViewSelectHour = LayoutInflater.from(getContext()).inflate(R.layout.pick_hour, null);
//                        final TextView eventTime =  addViewSelectHour.findViewById(R.id.eventtime);
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
//                        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
                        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
//                        Log.i("date fromated",sdf0.format(day)+" "+eventTime.getText());
                        Date addedDate = null;
                        try {
                            addedDate = sdf2.parse(sdf0.format(day)+" "+eventTime.getText());
                            Log.i("Added Event",addedDate.toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        List<String> userEntry = new ArrayList<>();
                        userEntry.add(eventName.getText().toString());
                        int[] userEntryLength = {200};
                        if(SecurityControler.securityMain(getContext(), userEntry, userEntryLength)) {
                            CalendarController.saveEvent(getContext(),userEntry.get(0), addedDate);
                        }
//                        ImportedEvent addedEvent = new ImportedEvent(authPlayer.getPseudo(),addedDate,eventName.getText().toString());
                        alertDialog.dismiss();
                    }
                });

            }
        });
        alertDialog.show();


//        AlertDialog ad = new AlertDialog.Builder(getActivity())
//                .create();
//        ad.setCancelable(false);
//        ad.show();
        }

    private ArrayList<ImportedEvent> collectEventByDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
        ArrayList<ImportedEvent> arrayList = new ArrayList<>();
        for(ImportedEvent ev:listImportedEvents){
            if (sdf.format(ev.getDate()).equals(sdf.format(date))){
                Log.i("Import event date : ",""+ev.getDate());
//                String initiator = ev.getUser();
//                String event = ev.getMessage();
//                String time = anotherSdfTime.format(ev.getDate());
//                String Date = anotherSdf.format(ev.getDate());
//                String month = ""+ev.getDate().getMonth();
//                String year = ""+ev.getDate().getYear();
                ImportedEvent events = new ImportedEvent(ev.getId(), ev.getInitiator(),ev.getDate(),ev.getMessage());
                arrayList.add(events);
            }
        }

        return arrayList;
    }
    private void saveEvent(String event, Date date){
        // TODO save sur mysql
//        Log.i("---   saveEvent   ---",date+" "+time);
        String[] params = new String[4];
        params[0]="create";
        params[1] = authPlayer.getPseudo();
        SimpleDateFormat eventDateTimeFormatSql = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.FRENCH);
        params[2] = eventDateTimeFormatSql.format(date)+":00";

//        try {
//            params[2] = eventDateTimeFormatSql.format(eventDateTimeFormat.parse(date+" "+time));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        params[3] = event;
        InsertRdvDao insertRdvDao = new InsertRdvDao(getContext());
        insertRdvDao.execute(params);
//        dbOpenHelper = new DBOpenHelper(context);
//        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
//        dbOpenHelper.saveEvent(event, time, date, month, year, database);
//        dbOpenHelper.close();
//        Toast.makeText(context, "Event saved localy", Toast.LENGTH_SHORT).show();
//        for (int i=0; i<10; i+=2){
//            eventsList.add(new Events("event "+(i+1), "00:00", ""+(11+i),"11","2019" ));
//        }
    }
//        public void login(){
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setCancelable(true);
//            View showView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_login_signin, null);
////        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
////        eventListTitle = showView.findViewById(R.id.eventsListTitle);
//            Button loginBtn = showView.findViewById(R.id.loginSigninLoginBtn);
//            final EditText usernameEt = showView.findViewById(R.id.loginSigninPseudoEt);
//            final EditText passwordEt = showView.findViewById(R.id.loginSigninPassEt);
//            builder.setView(showView);
//            alertDialog = builder.create();
//            alertDialog.show();
//
//            TextView signInLink = showView.findViewById(R.id.signinLink);
//            String str = "Pas encore inscrit ? Enregistrez-vous :)";
//            SpannableString ss = new SpannableString(str);
//            ClickableSpan clickableSpan = new ClickableSpan() {
//                @Override
//                public void onClick(@NonNull View widget) {
////                Toast.makeText(LoginSignin.this,"tata",Toast.LENGTH_LONG).show();
//                    final AlertDialog alertDialog;
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                    builder.setCancelable(true);
//                    View showView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_signin_layout, null);
//                    final TextView pass1 = showView.findViewById(R.id.SignInPopUpPassEt);
//                    final TextView pass2 = showView.findViewById(R.id.SignInPopUpPassEt2);
//                    final TextView pseudo = showView.findViewById(R.id.SignInPopUpPseudoEt);
//                    builder.setView(showView);
//                    alertDialog = builder.create();
//                    alertDialog.show();
//
//                    Button signinButton = showView.findViewById(R.id.SignInBtn);
//                    signinButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            List<String> userEntry = new ArrayList<>();
//                            userEntry.add(SecurityControler.firstToUpper(pseudo.getText().toString()));
//                            userEntry.add(pass1.getText().toString());
//                            userEntry.add(pass2.getText().toString());
//                            int[] userEntryLength = {20,20,20};
//                            if(SecurityControler.securityMain(getActivity(),userEntry, userEntryLength)) {
//                                LoginSigninControler.passCheck(getActivity(), userEntry.get(0), userEntry.get(1), userEntry.get(2));
//                            }
//                            alertDialog.dismiss();
//                        }
//                    });
//                }
//            };
//
//            ss.setSpan(clickableSpan,0,str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            signInLink.setText(ss);
//            signInLink.setMovementMethod(LinkMovementMethod.getInstance());
//
//            loginBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    List<String> userEntry = new ArrayList<>();
//                    userEntry.add(usernameEt.getText().toString());
//                    userEntry.add(passwordEt.getText().toString());
//                    int[] userEntryLength = {20,20};
//                    if(SecurityControler.securityMain(getContext(), userEntry, userEntryLength)){
//                        LoginSigninControler.logInCheck(getContext(), userEntry.get(0), userEntry.get(1));
//                    }
//                }
//            });
//        }

    public static void delete(){
        alertDialog.dismiss();
    }
    @Override
    public void onPause() {
        Log.i("CalendarFragment", "onPaused");
//        if (getFragmentManager().getBackStackEntryCount() > 0) {
//            getFragmentManager().popBackStack();
//        }
        super.onPause();
    }
}