package com.example.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.NewsFragment;
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
                        new HomeFragment()).commit();
            }
        });

        bottomNavigationView.setBackground(null);
        bottomNavigationView.findViewById(R.id.nav_home).setVisibility(View.GONE);
        bottomNavigationView.getMenu().getItem(1 ).setEnabled(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);



        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
                    new HomeFragment()).commit();
        }


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {

                        case R.id.nav_news:
                            selectedFragment = new NewsFragment();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                        case R.id.nav_home:



                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}