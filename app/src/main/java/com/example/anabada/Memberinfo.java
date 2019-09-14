package com.example.anabada;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Memberinfo {

    private String name;
    private String phonenumber;


    public Memberinfo(String name, String phonenumber){
        this.name =name;
        this.phonenumber = phonenumber;

    }
    public String getName(){
        return this.name;

    }
    public void setName(String name){
        this.name=name;
    }


    public String getPhonenumber(){
        return this.phonenumber;

    }
    public void setPhonenumber(String phonenumber){
        this.phonenumber=phonenumber;
    }
}
