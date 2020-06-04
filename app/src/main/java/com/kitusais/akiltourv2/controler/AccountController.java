package com.kitusais.akiltourv2.controler;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kitusais.akiltourv2.R;
import com.kitusais.akiltourv2.dao.EditAccountDao;

import static com.kitusais.akiltourv2.MainActivity.authPlayer;
import static com.kitusais.akiltourv2.ui.Calendrier.HomeFragment.contextCalendar;

public class AccountController {

    private static View showView;
    private static EditText confirmPass;
    public static void popupEdit(final Context context, final String field){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        AlertDialog alertDialog;
        showView = LayoutInflater.from(context).inflate(R.layout.popup_compte_edit, null);
//        RecyclerView recyclerView = showView.findViewById(R.id.EventsRdv);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(showView.getContext());
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setHasFixedSize(true);
        //EventRecyclerAdapter eventRecyclerAdapter = new EventRecyclerAdapter(showView.getContext()
        //        ,collectEventByDate(day));
        //recyclerView.setAdapter(eventRecyclerAdapter);
        //eventRecyclerAdapter.notifyDataSetChanged();
        if (field.equals("Password")){
            RelativeLayout confirmBlock = showView.findViewById(R.id.popoup_compte_edit_confirmBlock);
            confirmBlock.setVisibility(View.VISIBLE);
            confirmPass = showView.findViewById(R.id.popoup_compte_edit_value_confirme);
        }
        Button valider = showView.findViewById(R.id.popoup_compte_edit_valider);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText confirmEt = showView.findViewById(R.id.popoup_compte_edit_value);
                if((field == "Password" && confirmPass.getText().toString().equals(confirmEt.getText().toString())) || field == "Username"){
                    editSave(context,field);
                    Log.i("AccountController","savvvvve");
                }
            }
        });
        TextView textViewTitle = showView.findViewById(R.id.popoup_compte_edit_title);
        textViewTitle.setText("Modification de "+field);
        TextView textView = showView.findViewById(R.id.popoup_compte_edit_field);
        textView.setText(field);
        builder.setView(showView);

        //alertDialog = builder.create();

        //builder.setView(addView);
        alertDialog = builder.create();
        alertDialog.show();
    }
    public static void editSave(Context context, String field){
        EditText valueEt = showView.findViewById(R.id.popoup_compte_edit_value);
        String oldPass = "";
        if(field.equals("Password")){
            oldPass = ((EditText)showView.findViewById(R.id.popoup_compte_edit_value_ancien)).getText().toString();
        }
        EditAccountDao insertRdvToDatabase = new EditAccountDao(context);
        insertRdvToDatabase.execute(""+authPlayer.getId(),valueEt.getText().toString(),field, oldPass);
    }
}
