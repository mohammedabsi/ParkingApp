package com.example.parkingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class HomeActivity extends AppCompatActivity {
   private BottomNavigationView bottomNavigationView;
    private CoordinatorLayout homeContainer ;
    private FloatingActionButton fab;
    private BottomAppBar bottomAppBar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fab = findViewById(R.id.fab);
        homeContainer = findViewById(R.id.homeContainer);

        Snackbar snackbar = Snackbar.make(homeContainer, "Login success", Snackbar.LENGTH_LONG);
        snackbar.setAnchorView(R.id.fab);
        snackbar.show();
        bottomNavigationView = findViewById(R.id.bottomBar);



        bottomNavigationView.setBackground(null);
//        bottomNavigationView.findViewById(R.id.nav_home).setVisibility(View.GONE);
//        bottomNavigationView.getMenu().getItem(0 ).setCheckable(false);


        // todo : enable the pressed nav icon again with this
//        NavigationBottom.getMenu().setGroupCheckable(0, true, true);

    }
}