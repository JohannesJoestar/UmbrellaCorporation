package com.example.umbrellacorporation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

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
    String token;

    //// Methods
    // AppCompatActivity implementation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
