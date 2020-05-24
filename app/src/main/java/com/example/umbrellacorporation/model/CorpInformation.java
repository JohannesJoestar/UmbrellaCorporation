package com.example.umbrellacorporation.model;

import java.io.Serializable;

public class CorpInformation implements Serializable {

    //// Properties
    // Attributes
    private String title;
    private String message;

    //// Constructors
    // Parametric
    public CorpInformation(String title, String message){
        setTitle(title);
        setMessage(message);
    }

    //// Methods
    // Access modifiers
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
}
