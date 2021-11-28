package com.example.parkingapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParkClass {

    @SerializedName("id")
    @Expose
    private int id  ;

    @SerializedName("status")
    @Expose
    private int  status  ;







    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
