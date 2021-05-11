package com.example.mapapp;

import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import com.example.mapapp.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, Updatable {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private String markerText;
    private List<Marker> markerList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Repo.r().setup(this, markerList);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLongClickListener(latLng -> {
            System.out.println("Long press: " + latLng);
            markerDialog(latLng);
        });
    }

    private void addMarker(LatLng latLng, String markerText){
        mMap.addMarker(new MarkerOptions().position(latLng).title(markerText));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    private void markerDialog(LatLng latLng){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                                          .setTitle("New marker")
                                          .setMessage("What is your marker called?");
        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                markerText = input.getText().toString();
                addMarker(latLng, markerText);
                Repo.r().addMarker(latLng, markerText);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void update(Object o){
        System.out.println("Update called");
        mMap.clear();
        for (Marker marker : markerList) {
            mMap.addMarker(new MarkerOptions().position(marker.getPosition()).title(marker.getTitle()));
        }
    }
}