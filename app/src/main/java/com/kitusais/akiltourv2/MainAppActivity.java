package com.kitusais.akiltourv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;
import com.kitusais.akiltourv2.dao.GetRdvDao;
import com.kitusais.akiltourv2.view.EventDetails;

import static com.kitusais.akiltourv2.MainActivity.alreadyImported;
import static com.kitusais.akiltourv2.MainActivity.authPlayer;
import static com.kitusais.akiltourv2.MainActivity.previousFragment;
//import static com.kitusais.akiltourv2.controler.GameDoubleButtonControler.won;


public class MainAppActivity extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;
    //public static String baseUrl ="http://kitusais.orgfree.com/droid_connect_2/";
    public static String baseUrl ="http://192.168.1.21/up2net/";
    public static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Context context;
    private AlertDialog alertDialog;
    private boolean success = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("mainAppActivity - previousFrag"," - "+authPlayer.getId());
        setContentView(R.layout.activity_main_app);
        Toolbar toolbar = findViewById(R.id.toolbar);
        context = this;
        Log.i("MainAppActivity","launched");
        Intent intent = getIntent();
        String previousFragment = null;
        previousFragment = intent.getStringExtra("previousFragment");
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }

        if (!alreadyImported){
            Log.i("MainAppActivity","Rdv Not Imported");
            GetRdvDao getRdvDao = new GetRdvDao(this);
            getRdvDao.execute();
        }
        if(authPlayer == null){
//            login(context);
        }else{
//           alertDialog.dismiss();
        }

        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        // Check random game selected


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_result, R.id.nav_send)//
                .setDrawerLayout(drawer)
                .build();
        //mAppBarConfiguration = new AppBarConfiguration.Builder(
        //        R.id.nav_home, R.id.nav_gallery, R.id.nav_gallery,
         //       R.id.nav_gallery, R.id.nav_gallery, R.id.nav_home)//
         //       .setDrawerLayout(drawer)
         //       .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);


        NavInflater navInflater = navController.getNavInflater();
        NavGraph graph = navInflater.inflate(R.navigation.mobile_navigation);

        if ("Calendar".equals(previousFragment)) {
            graph.setStartDestination(R.id.nav_home);
        //}else if("Resultat".equals(previousFragment)){
        //    graph.setStartDestination(R.id.);
        }else if ("EventDetail".equals(previousFragment)) {
            graph.setStartDestination(R.id.nav_home);
            Intent intent1=new Intent(context, EventDetails.class);
            context.startActivity(intent1);
        } else {
            graph.setStartDestination(R.id.nav_gallery);
        }

        navController.setGraph(graph);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        Log.i("MainAppActivity","onBackPressed GGGGGG");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        previousFragment = null;
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
//            super.onBackPressed();
        }
    }
}
