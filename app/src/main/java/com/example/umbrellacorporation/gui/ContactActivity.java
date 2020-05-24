package com.example.umbrellacorporation.gui;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.umbrellacorporation.R;
import com.example.umbrellacorporation.model.CorpContact;
import com.example.umbrellacorporation.model.CorpInformation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ContactActivity extends FragmentActivity implements OnMapReadyCallback {

    //// Properties
    // References
    private String information;
    private DatabaseReference DBR;
    private FirebaseDatabase DB;
    private GoogleMap mMap;

    //// Methods
    // Activity life cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        // Get information type
        Bundle extras = getIntent().getExtras();
        if (extras.keySet().contains(InformationActivity.I_EXTRA_INFO_TYPE)){

            // Activity started with valid Intent
            information = getIntent().getExtras()
                    .getString(InformationActivity.I_EXTRA_INFO_TYPE);

        } else {

            // Invalid intent
            Toast.makeText(getApplicationContext(),
                    R.string.weird_intent,
                    Toast.LENGTH_LONG
            ).show();
            finish();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    // OnMapReadyCallback implementation
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Prepare Firebase
        DB = FirebaseDatabase.getInstance();
        DBR = DB.getReference("/information/" + information);

        // No need for realtime feed, read "once"
        // For real time feed, the only difference would be the method used on the reference.
        DBR.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Process snapshot
                CorpContact data = dataSnapshot.getValue(CorpContact.class);

                // Add the marker
                LatLng bermuda = new LatLng(data.getLatitude(), data.getLongitude());
                mMap.addMarker(new MarkerOptions().position(bermuda).title(
                        data.getPhone()
                ));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(bermuda));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ContactActivity", databaseError.toString());

                // Inform user and exit
                Toast.makeText(getApplicationContext(),
                        databaseError.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
                finish();
            }
        });


    }
}
