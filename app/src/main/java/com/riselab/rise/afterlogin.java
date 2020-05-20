package com.riselab.rise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Vibrator;
import android.view.MenuItem;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class afterlogin extends AppCompatActivity {
    ImageView logoal ;
    Vibrator vib ;
    MenuItem profile ;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private AppBarConfiguration mAppBarConfiguration;
String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterlogin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();

        type = bundle.getString("type");


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        profile =  findViewById(R.id.action_profile);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.afterlogin, menu);

//        profile.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//                vib.vibrate(200);
//                dialogprofile dialog = new dialogprofile();
//                dialog.show(getSupportFragmentManager(),"Profile");
//                return false;
//            }
//        });

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void upload(MenuItem item) {
        if(type.equals("admin")) {
            vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vib.vibrate(200);
            dialogupload dialog = new dialogupload();
            dialog.show(getSupportFragmentManager(), "Upload");
        }
        else {
            Toast.makeText(getApplicationContext(),"Only R.I.S.E Members can upload",Toast.LENGTH_LONG).show();
            vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vib.vibrate(200);
            try {
                TimeUnit.MILLISECONDS.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            vib.vibrate(200);
        }

    }

    public void profile(MenuItem item) {
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(200);
        dialogprofile dialog = new dialogprofile();
        dialog.show(getSupportFragmentManager(),"Profile");

    }

    public void signoutfirebase(MenuItem item) {
        firebaseAuth.signOut();
        Intent i = new Intent(getApplicationContext(),splash.class);
        startActivity(i);
        finish();
    }
}
