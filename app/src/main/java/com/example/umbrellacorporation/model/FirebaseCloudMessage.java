package com.example.umbrellacorporation.model;

public class FirebaseCloudMessage {

    //// Properties
    // Attributes
    private String email, text, token;

    //// Constructors
    // Parametric
    public FirebaseCloudMessage(String email, String text, String token){
        setEmail(email);
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
}
