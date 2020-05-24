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
import android.view.View;
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
    // References
    private Resources resources;
    private IntentFilter IF;
    private BroadcastReceiver BR;
    private String token;
    // Constants
    public static String I_EXTRA_F_TOKEN = "token";

    //// Methods
    // Activity life cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //// Attach events
        // "History" button
        findViewById(R.id.btnHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(MainActivity.this, InformationActivity.class)
                                .putExtra(InformationActivity.I_EXTRA_INFO_TYPE, "history")
                );
            }
        });
        // "Vision" button
        findViewById(R.id.btnVision).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(MainActivity.this, InformationActivity.class)
                                .putExtra(InformationActivity.I_EXTRA_INFO_TYPE, "vision")
                );
            }
        });
        // "Contact" button
        findViewById(R.id.btnAddress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(MainActivity.this, ContactActivity.class)
                                .putExtra(InformationActivity.I_EXTRA_INFO_TYPE, "contact")
                );
            }
        });
        // "Support" button
        findViewById(R.id.btnSupport).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(MainActivity.this, SupportActivity.class)
                                .putExtra(I_EXTRA_F_TOKEN, token)
                );
            }
        });
        resources = getResources();

        // Setup BroadcastReceiver for toasting Firebase notifications
        BR = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle extras = intent.getExtras();

                // Alerts more suitable than Toasts for notifications
                AlertDialog.Builder ADB = new AlertDialog.Builder(context);
                ADB.setTitle(extras.getString(CloudMessagingService.I_EXTRA_NOTIFICATION_TITLE))
                    .setMessage(extras.getString(CloudMessagingService.I_EXTRA_NOTIFICATION_BODY))
                    .setNegativeButton(R.string.adb_ok, null)
                    .show();
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
        FirebaseMessaging.getInstance().subscribeToTopic("notifications")
            .addOnCompleteListener(new OnCompleteListener<Void>() {
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
