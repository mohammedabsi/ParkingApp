package com.example.parkingapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "com.example.parkingapp";


    ProgressBar parking_progressBar;
    RecyclerView parking_Recycler;
    LinearLayoutManager layoutManager;
    ParkAdapter adapter;
    List<ParkClass> parkClassList = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        parking_progressBar = view.findViewById(R.id.parking_progressBar);
        parking_Recycler = view.findViewById(R.id.parking_Recycler);
        layoutManager = new GridLayoutManager(getActivity(), 4);
        parking_Recycler.setLayoutManager(layoutManager);
        adapter = new ParkAdapter(parkClassList);
        parking_Recycler.setAdapter(adapter);


        content();

        return view;
    }

    public void content() {
        parking_progressBar.setVisibility(View.VISIBLE);


        RetrofitClient.getRetrofitClient().getPark().enqueue(new Callback<List<ParkClass>>() {
            @Override
            public void onResponse(Call<List<ParkClass>> call, Response<List<ParkClass>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    parkClassList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    parking_progressBar.setVisibility(View.GONE);

                    String res = "";
                    for (ParkClass parkclass : response.body()) {
                        res += parkclass.getId() + " " + parkclass.getStatus() + " " + "\n";

                    }
                    Log.d("outRes", res);
                }
            }

            @Override
            public void onFailure(Call<List<ParkClass>> call, Throwable t) {
                parking_progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Error is :" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

//        refresh(5000);

    }

//    public void refresh(int milliseconds) {
//        final Handler handler = new Handler();
//        final Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                content();
//            }
//        };
//        handler.postDelayed(runnable, milliseconds);
//
//    }
}