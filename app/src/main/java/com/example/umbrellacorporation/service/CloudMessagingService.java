package com.example.umbrellacorporation.service;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class CloudMessagingService extends FirebaseMessagingService {

    //// Methods
    // FirebaseMessagingService Implementation
    public void onMessageReceived(RemoteMessage message){
        Map<String, String> payload = message.getData();
    }
}
