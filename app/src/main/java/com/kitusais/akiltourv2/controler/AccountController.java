package com.kitusais.akiltourv2.controler;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kitusais.akiltourv2.R;

public class AccountController {
    public static void popupEdit(Context context, String field){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        AlertDialog alertDialog;
        View showView = LayoutInflater.from(context).inflate(R.layout.popup_compte_edit, null);
//        RecyclerView recyclerView = showView.findViewById(R.id.EventsRdv);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(showView.getContext());
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setHasFixedSize(true);
        //EventRecyclerAdapter eventRecyclerAdapter = new EventRecyclerAdapter(showView.getContext()
        //        ,collectEventByDate(day));
        //recyclerView.setAdapter(eventRecyclerAdapter);
        //eventRecyclerAdapter.notifyDataSetChanged();
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
}
