package com.example.umbrellacorporation.gui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.umbrellacorporation.R;
import com.example.umbrellacorporation.model.SupportTicket;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SupportActivity extends AppCompatActivity {

    //// Properties
    // Components
    private EditText etEmail;
    private EditText etMessage;
    // References
    private DatabaseReference DBR;
    private FirebaseDatabase DB;
    private String token;

    //// Methods
    // Activity life cycle

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        // Check intent extras
        Bundle extras = getIntent().getExtras();
        if (extras.keySet().contains(MainActivity.I_EXTRA_F_TOKEN)){
            token = extras.getString(MainActivity.I_EXTRA_F_TOKEN);
        } else {

            // Invalid intent
            Toast.makeText(getApplicationContext(),
                    R.string.weird_intent,
                    Toast.LENGTH_LONG
            ).show();
            finish();
        }

        // Prepare Firebase
        DB = FirebaseDatabase.getInstance();
        DBR = DB.getReference("/tickets");

        // Get components
        etEmail = findViewById(R.id.etEmail);
        etMessage = findViewById(R.id.etMessage);

        //// Attach events
        // "Send" button
        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String message = etMessage.getText().toString();

                // Validate fields
                if (email.isEmpty() || message.isEmpty()){

                    // Inform user
                    Toast.makeText(getApplicationContext(),
                        R.string.no_blank_fields,
                        Toast.LENGTH_LONG
                    ).show();

                } else {

                    // Build message for Firebase
                    SupportTicket ticket = new SupportTicket(email, message, token);

                    // Write to Firebase
                    DBR.push().setValue(ticket).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            new AlertDialog.Builder(SupportActivity.this)
                                    .setTitle(R.string.support_thanks)
                                    .setMessage(R.string.support_soon)
                                    .setPositiveButton(R.string.adb_ok, null)
                                    .show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("SupportActivity", e.getMessage());

                            // Inform user
                            Toast.makeText(getApplicationContext(),
                                e.getMessage(),
                                Toast.LENGTH_LONG
                            ).show();
                        }
                    });
                }
            }
        });
    }
}
