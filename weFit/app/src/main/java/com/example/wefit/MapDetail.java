package com.example.wefit;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MapDetail extends FragmentActivity implements OnMapReadyCallback {
    TextView dist, time, cal, speed;
    ArrayList<LatLng> points;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        dist = findViewById(R.id.distance_txtroute);
        time = findViewById(R.id.time_textroute);
        cal = findViewById(R.id.cal_textroute);
        speed = findViewById(R.id.speed_textroute);
        dist.setText(getIntent().getStringExtra("dist"));
        time.setText(getIntent().getStringExtra("time"));
        cal.setText(getIntent().getStringExtra("cal"));
        speed.setText(getIntent().getStringExtra("speed"));
        String p = getIntent().getStringExtra("points");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<LatLng>>() {}.getType();
        points = gson.fromJson(p, type);


        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    SupportMapFragment supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.activityMapRoute);
                    assert supportMapFragment != null;
                    supportMapFragment.getMapAsync(MapDetail.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Polyline polyline = googleMap.addPolyline(new PolylineOptions().width(4).color(Color.BLACK));
        polyline.setPoints(points);
    }
}
