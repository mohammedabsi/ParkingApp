package com.example.parkingapp;


import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.Timer;
import java.util.TimerTask;

public class OnBoardAdapter extends FragmentStatePagerAdapter {


    final Handler handler = new Handler();
    public Timer swipeTimer ;







public OnBoardAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                return new FragmentOne();
            case 1:
                return new FragmentTwo();
            case 2:
                return new FragmentThree();
                default:
                    return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }


    public void setTimer(final ViewPager myPager, int time , final int curPage){

        final Runnable Update = new Runnable() {
            int NUM_PAGES = 3;
            int currentPage = curPage ;
            public void run() {
                if (currentPage == NUM_PAGES ) {
                    currentPage = 0;
                }
                myPager.setCurrentItem(currentPage++, true);
            }
        };

        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 0, time*1000);

    }
    public void stopTimer(){
        swipeTimer.cancel();
    }
}
