package com.example.parkingapp;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;


public class OnBoardActivity extends AppCompatActivity {
    private ViewPager viewPager;
    ConstraintLayout vb;
    private OnBoardAdapter adapter;
    TextView skip;
private boolean skiptrigger = false ;
private String SKIP_TRIGGER = "com.example.youcan.SKIP_TRIGGER" ;

     Integer[] colors = null;
    DotsIndicator tab_indicator;
    int x = 0;


    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_on_board_pager);
        getSupportActionBar().hide();

        tab_indicator = findViewById(R.id.tab_indicator);
        viewPager = findViewById(R.id.view_pager);
        vb = findViewById(R.id.vb);
        skip = findViewById(R.id.skip);

        adapter = new OnBoardAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        tab_indicator.setViewPager(viewPager);


        viewPager.setCurrentItem(0);
        adapter.setTimer(viewPager, 3, x);
        viewPager.setOffscreenPageLimit(1);

        Integer x1 = getResources().getColor(R.color.greenpallete3);
        Integer x2 = getResources().getColor(R.color.greenpallete2);
        Integer x3 = getResources().getColor(R.color.greenpallete1);
        Integer[] colors_temp = {x3, x2, x1};
        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                x = position;
                if (position == 2) {
                    adapter.stopTimer();
                    skip.setText("Get Started");


                } else {
                    skip.setText("Skip");

                }

               }


            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void onPause() {
        adapter.stopTimer();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        viewPager.setCurrentItem(x);
        adapter.setTimer(viewPager, 3, x);
        super.onRestart();

    }

    @Override
    public void onBackPressed() {
        viewPager.setCurrentItem(x);
        adapter.setTimer(viewPager, 3, x);
        super.onBackPressed();
    }


    public void goLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
