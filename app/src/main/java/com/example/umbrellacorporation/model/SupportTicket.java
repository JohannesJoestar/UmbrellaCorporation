package com.example.umbrellacorporation.model;

import java.io.Serializable;

public class SupportTicket implements Serializable {

    //// Properties
    // Attributes
    private String email, phone, text, token;

    //// Constructors
    // Default
    public SupportTicket(){};
    // Parametric
    public SupportTicket(String email, String phone, String text, String token){
        setEmail(email);
        setPhone(phone);
        setText(text);
        setToken(token);
    }

    //// Methods
    // Access modifiers
    public String getEmail() {
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getText(){
        return text;
    }
    public void setText(String text){
        this.text = text;
    }
    public String getToken(){
        return token;
    }
    public void setToken(String token){
        this.token = token;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
