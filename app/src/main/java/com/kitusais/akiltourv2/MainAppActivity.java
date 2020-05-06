package com.kitusais.akiltourv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import androidx.navigation.NavController;
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
import android.view.SurfaceControl;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
//import com.google.firebase.database.FirebaseDatabase;
import com.kitusais.akiltourv2.controler.GetRdvDao;
import com.kitusais.akiltourv2.model.Player;
import com.kitusais.akiltourv2.ui.Calendrier.HomeFragment;
import com.kitusais.akiltourv2.ui.miniJeux.SlideshowFragment;
import com.kitusais.akiltourv2.view.ResultFragment;

import static com.kitusais.akiltourv2.MainActivity.alreadyImported;
import static com.kitusais.akiltourv2.MainActivity.authPlayer;
import static com.kitusais.akiltourv2.MainActivity.numRandomGame;
//import static com.kitusais.akiltourv2.controler.GameDoubleButtonControler.won;

public class MainAppActivity extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;
    //public static String baseUrl ="http://kitusais.orgfree.com/droid_connect_2/";
    public static String baseUrl ="http://192.168.1.21/up2net/";
//    public static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Context context;
    private AlertDialog alertDialog;
    private boolean success = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_app);
        Toolbar toolbar = findViewById(R.id.toolbar);
        context = this;
        Log.i("MainAppActivity","launched");
//        Intent intent = getIntent();
//        String previousFragment = null;
//        previousFragment = intent.getStringExtra("previousFragment");
//        if(previousFragment!=null) {
//            if (previousFragment.equals("calendar")) {
////                FragmentManager manager = getFragmentManager();
////                FragmentTransaction transaction = manager.beginTransaction();
////                transaction.add(R.id.nav_host_fragment, new HomeFragment(),null);
////                transaction.addToBackStack(null);
////                transaction.commit();
////                getSupportFragmentManager().beginTransaction()
////                        .add(R.id.nav_host_fragment, new HomeFragment()).commit();
//
//            Log.i("MainAppActivity", "previousFragment = calendar");
//            }
//        }
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
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)//
                .setDrawerLayout(drawer)
                .build();
        //mAppBarConfiguration = new AppBarConfiguration.Builder(
        //        R.id.nav_home, R.id.nav_gallery, R.id.nav_gallery,
         //       R.id.nav_gallery, R.id.nav_gallery, R.id.nav_home)//
         //       .setDrawerLayout(drawer)
         //       .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
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
