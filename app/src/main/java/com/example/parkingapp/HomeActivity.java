package com.example.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout home_fragment_container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        home_fragment_container = findViewById(R.id.home_fragment_container);


        Toast.makeText(HomeActivity.this, "Login success", Toast.LENGTH_SHORT).show();
        bottomNavigationView = findViewById(R.id.bottomNavBar);


        bottomNavigationView.setBackground(null);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);


        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            bottomNavigationView.getMenu().getItem(1).setChecked(true);
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
                            selectedFragment = new HomeFragment();
                            break;


                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}