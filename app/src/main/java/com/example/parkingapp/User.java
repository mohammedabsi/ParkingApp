package com.example.parkingapp;

public class User {
    public String username, email, password, userId;
    public Boolean type_s ;
    public int status_s;

    public User() {

    }

    public User(String username, String userId, String password, String email, Boolean type_s , int status_s) {
        this.username = username;
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.type_s = type_s;
        this.status_s = status_s;


    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getType_s() {
        return type_s;
    }

    public void setType_s(Boolean type) {
        this.type_s = type_s;
    }

    public int getStatus_s() {
        return status_s;
    }

    public void setStatus_s(int status) {
        this.status_s = status_s;
    }
}
