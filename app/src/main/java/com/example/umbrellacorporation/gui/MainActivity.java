package com.example.umbrellacorporation.gui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.umbrellacorporation.R;
import com.example.umbrellacorporation.service.CloudMessagingService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    //// Properties
    // Components
    Button btnHistory, btnVision, btnAddress, btnSupport;
    // References
    Resources resources = getResources();
    IntentFilter IF;
    BroadcastReceiver BR;
    String token;

    //// Methods
    // Activity life cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup BroadcastReceiver for toasting Firebase notifications
        BR = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle extras = intent.getExtras();

                // Alerts more suitable than Toasts for notifications
                AlertDialog.Builder ADB = new AlertDialog.Builder(context);
                ADB.setTitle(extras.getString(CloudMessagingService.I_EXTRA_NOTIFICATION_TITLE));
                ADB.setTitle(extras.getString(CloudMessagingService.I_EXTRA_NOTIFICATION_BODY));
                ADB.setNegativeButton(R.string.adb_ok, null);
            }
        };
        IF = new IntentFilter(CloudMessagingService.BR_FILTER_ACTION);

        // Test Firebase connection and obtain device token
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()){
                            token = task.getResult().getToken();
                        }
                    }
                });

        // Subscribe to push notifications
        FirebaseMessaging.getInstance().subscribeToTopic(
                resources.getString(R.string.notifications_channel)
        ).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    // Inform user of subscription state
                    Toast.makeText(
                            getApplicationContext(),
                            (task.isSuccessful() ?
                                    resources.getString(R.string.subscription_ok) :
                                    resources.getString(R.string.subscription_fail)),
                            Toast.LENGTH_LONG
                    ).show();
                }
            });
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Registering BroadcastReceiver
        if (BR != null) registerReceiver(BR, IF);

    }
    @Override
    protected void onPause(){
        super.onPause();

        // Unregistering BroadcastReceiver
        if (BR != null) unregisterReceiver(BR);

    }
}
