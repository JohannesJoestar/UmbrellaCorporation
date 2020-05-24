package com.example.umbrellacorporation.gui;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.umbrellacorporation.R;
import com.example.umbrellacorporation.model.Information;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InformationActivity extends AppCompatActivity {

    //// Properties
    // Reference
    DatabaseReference DBR;
    FirebaseDatabase DB;
    // Constants
    public static String I_EXTRA_INFO_TYPE = "type";

    //// Methods
    // Activity life cycle
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // Get information type
        Bundle extras = getIntent().getExtras();
        if (extras.keySet().contains(I_EXTRA_INFO_TYPE)){

            // Activity started with valid Intent
            String information = getIntent().getExtras().getString(I_EXTRA_INFO_TYPE);

            // Prepare Firebase
            DB = FirebaseDatabase.getInstance();
            DBR = DB.getReference("/information/" + information);

            // No need for realtime feed, read "once"
            // For real time feed, the only difference would be the method used on the reference.
            DBR.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    // Process snapshot
                    Information data = dataSnapshot.getValue(Information.class);

                    // Use processed information
                    ((TextView) findViewById(R.id.txtTitle)).setText(data.getTitle());
                    ((TextView) findViewById(R.id.txtMessage)).setText(data.getMessage());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("InformationActivity", databaseError.toString());

                    // Inform user and exit
                    Toast.makeText(getApplicationContext(),
                            databaseError.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                    finish();
                }
            });

        } else {

            // Invalid intent
            Toast.makeText(getApplicationContext(),
                    R.string.weird_intent,
                    Toast.LENGTH_LONG
            ).show();
            finish();
        }
    }
}
