package com.example.parkingapp;

public class AddNew {
    public String  header , descNew  ,imgsource;


    public AddNew() {

    }

    public AddNew(String header, String descNew, String imgsource) {
        this.header = header;
        this.descNew = descNew;
        this.imgsource = imgsource;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescNew() {
        return descNew;
    }

    public void setDescNew(String descNew) {
        this.descNew = descNew;
    }

    public String getImgsource() {
        return imgsource;
    }

    public void setImgsource(String imgsource) {
        this.imgsource = imgsource;
    }
}
