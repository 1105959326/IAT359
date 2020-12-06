package com.example.wefit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class RecordingActivity extends Fragment implements View.OnClickListener, OnMapReadyCallback {

    private ImageButton runBtn, rideBtn, startBtn;
    private int state = 1;
    private Button firstButton;
    private SecondFragment secondFragment;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_record, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        findId(view);
        fetchLocation();
    }

    private void findId(View view){
        runBtn = view.findViewById(R.id.run_button);
        rideBtn = view.findViewById(R.id.ride_button);
        startBtn = view.findViewById(R.id.startRec);

        runBtn.setOnClickListener(this);
        rideBtn.setOnClickListener(this);
        startBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.run_button){
            state = 1;
        }
        if(v.getId() == R.id.ride_button){
            state = 2;
        }

        if(v.getId() == R.id.startRec){
            if (state == 1){

                Intent i = new Intent(getContext(), RecordPage.class);
                i.putExtra("state", "RUN");
                i.putExtra("burnedCal", 62);
                getContext().startActivity(i);

            }if (state == 2){
                Intent i = new Intent(getContext(), RecordPage.class);
                i.putExtra("state", "RIDE");
                i.putExtra("burnedCal", 29);
                getContext().startActivity(i);

            }
        }

    }
    private void fetchLocation() {

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    if (!isAdded()) return;
                    SupportMapFragment supportMapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.myMap);
                    assert supportMapFragment != null;
                    supportMapFragment.getMapAsync(RecordingActivity.this);
                }
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        googleMap.addMarker(markerOptions);
    }
}