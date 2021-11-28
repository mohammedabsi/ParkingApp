package com.example.parkingapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String base_Url="http://104.131.182.223/" ;
    private static Retrofit retrofit = null;

    public static ApiInterface getRetrofitClient(){
        if (retrofit==null){

            retrofit = new Retrofit.Builder()
                    .baseUrl(base_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(ApiInterface.class);
    }

}
