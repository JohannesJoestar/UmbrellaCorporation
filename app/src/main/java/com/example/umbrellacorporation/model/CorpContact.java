package com.example.umbrellacorporation.model;

import java.io.Serializable;

public class CorpContact implements Serializable {

    //// Properties
    // Attributes
    private String phone;
    private int latitude;
    private int longitude;

    //// Constructors
    // Default
    public CorpContact(){};
    // Parametric
    public CorpContact(String phone, int latitude, int longitude){
        setPhone(phone);
        setLatitude(latitude);
        setLongitude(longitude);
    }

    //// Methods
    // Access modifiers
    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public int getLatitude(){
        return latitude;
    }
    public void setLatitude(int latitude){
        this.latitude = latitude;
    }
    public int getLongitude(){
        return longitude;
    }
    public void setLongitude(int longitude){
        this.longitude = longitude;
    }
}
