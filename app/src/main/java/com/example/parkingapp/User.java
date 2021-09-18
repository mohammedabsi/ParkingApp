package com.example.parkingapp;

public class User {
    public String  username , email , password , userId ;
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
}
