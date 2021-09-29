package com.example.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminMainActivity extends AppCompatActivity {
    private BottomNavigationView bottomAdminNavbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        bottomAdminNavbar = findViewById(R.id.bottomAdminNavbar);


        bottomAdminNavbar.setBackground(null);
        bottomAdminNavbar.setOnNavigationItemSelectedListener(navListener);


        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.Admin_fragment_container,
                    new AcceptRequestFragment()).commit();
        }


    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {

                        case R.id.nav_addnews:
                            selectedFragment = new AddNewsFragment();
                            break;

                        case R.id.nav_pendinguser:
                            selectedFragment = new AcceptRequestFragment();
                            break;




                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.Admin_fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}