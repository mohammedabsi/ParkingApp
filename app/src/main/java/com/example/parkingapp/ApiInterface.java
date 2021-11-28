package com.example.parkingapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("ParkingCar/public/get_parking")
     Call<List<ParkClass>> getPark();
}
