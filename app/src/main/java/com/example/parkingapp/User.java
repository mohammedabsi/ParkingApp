package com.example.parkingapp;

public class User {
    public String  username , email , password , userId  ;
    public Boolean type ;
    public User (){

    }
    public User(String username ,String userId, String password ,String  email , Boolean type ){
     this.username = username ;
        this.userId = userId ;
        this.password = password ;
     this.email = email ;


     this.type = type ;

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

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }
}
