package com.example.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout home_fragment_container;
    private FirebaseFirestore firestore;
    private Fragment selectedFragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        home_fragment_container = findViewById(R.id.home_fragment_container);
        firestore = FirebaseFirestore.getInstance();

        Toast.makeText(HomeActivity.this, "Login success", Toast.LENGTH_SHORT).show();
        bottomNavigationView = findViewById(R.id.bottomNavBar);


        bottomNavigationView.setBackground(null);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);


        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            bottomNavigationView.getMenu().getItem(2).setChecked(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
                    new ProfileFragment()).commit();
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentemail = user.getEmail();
        DocumentReference dr = firestore.collection("Teacher_Side").document(currentemail);

        dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists()) {
                    if (task.getResult().getBoolean("type_s") == false) {

//                        Fragment selectedFragment = new ProfileFragment();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
//                                selectedFragment).commit();
                        bottomNavigationView.getMenu().getItem(0).setEnabled(false);
                        bottomNavigationView.getMenu().getItem(1).setEnabled(false);


                            Toast.makeText(HomeActivity.this, "wait for admin to approve your account", Toast.LENGTH_SHORT).show();


                    }else {

                        bottomNavigationView.getMenu().getItem(0).setEnabled(true);
                        bottomNavigationView.getMenu().getItem(1).setEnabled(true);
                        Toast.makeText(HomeActivity.this, "Account is approved welcome "+task.getResult().getString("username"), Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Log out success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();

        }

        return super.onOptionsItemSelected(item);
    }
}