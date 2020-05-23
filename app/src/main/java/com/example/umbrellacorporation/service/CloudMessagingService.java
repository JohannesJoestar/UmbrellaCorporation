package com.example.umbrellacorporation.service;

import android.util.Log;

import com.example.umbrellacorporation.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Iterator;
import java.util.Map;

public class CloudMessagingService extends FirebaseMessagingService {

    //// Methods
    // FirebaseMessagingService Implementation
    @Override
    public void onMessageReceived(RemoteMessage message){
        logPayload(message.getData());
    }
    // Utility
    private void logPayload(Map<String, String> payload){
        Iterator<String> iterator = payload.keySet().iterator();

        // Iterate message payload
        while (iterator.hasNext()){
            String key = iterator.next();
            String value = payload.get(key);

            Log.d("MainActivity", String.format(
                    getResources().getString(R.string.payload_log_format), key, value)
            );
        }
    }
}
