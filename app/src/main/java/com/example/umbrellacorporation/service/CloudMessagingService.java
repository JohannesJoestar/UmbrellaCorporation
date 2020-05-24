package com.example.umbrellacorporation.service;

import android.content.Intent;
import android.util.Log;

import com.example.umbrellacorporation.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Iterator;
import java.util.Map;

import static com.google.firebase.messaging.RemoteMessage.*;

public class CloudMessagingService extends FirebaseMessagingService {

    //// Properties
    // Constants
    public static String BR_FILTER_ACTION = "new_notification";
    public static String I_EXTRA_NOTIFICATION_TITLE = "title";
    public static String I_EXTRA_NOTIFICATION_BODY = "body";

    //// Methods
    // FirebaseMessagingService Implementation
    @Override
    public void onMessageReceived(RemoteMessage message){
        logPayload(message.getData());

        // "Foreground" notification
        broadcastNotification(message.getNotification());
    }
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        // DEBUG
        Log.e("CloudMessagingService", token);
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
    private void broadcastNotification(Notification notification){

        // Broadcasting a message from a Service to be received by Activity instances.
        Intent intent = new Intent(BR_FILTER_ACTION);
        intent.putExtra(I_EXTRA_NOTIFICATION_TITLE, notification.getTitle());
        intent.putExtra(I_EXTRA_NOTIFICATION_BODY, notification.getBody());
        sendBroadcast(intent);

    }
}
