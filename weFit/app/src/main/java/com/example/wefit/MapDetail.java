package com.example.wefit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
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
import java.util.Collections;
import java.util.List;

public class MapDetail extends FragmentActivity implements OnMapReadyCallback {
    TextView dist, time, cal, speed, type;
    ArrayList<LatLng> postions = new ArrayList<LatLng>();
    LatLng points;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //create the layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        //find textview and add string to that
        type = findViewById(R.id.type_route);
        dist = findViewById(R.id.distance_txtroute);
        time = findViewById(R.id.time_textroute);
        cal = findViewById(R.id.cal_textroute);
        speed = findViewById(R.id.speed_textroute);
        type.setText("Type\n" + getIntent().getStringExtra("type"));
        dist.setText("Distance\n" + getIntent().getStringExtra("dist"));
        time.setText("Time\n" + getIntent().getStringExtra("time"));
        cal.setText("Cal Burned\n" + getIntent().getStringExtra("cal"));
        speed.setText("Speed\n" + getIntent().getStringExtra("speed"));

        //get points string and convert to double
        String p = getIntent().getStringExtra("points");
        int x = 0;
        String[] points = p.split(",");
        while (x < points.length){

            if ( points[x].length() > 1 && points[x + 1].length() > 1){
                LatLng latLng = new LatLng(Double.parseDouble(points[x].toString()), Double.parseDouble(points[x + 1].toString()));
                x += 2;
                postions.add(latLng);
            }
            else break;
        }

        //call location listener
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

        //set the back button
        Button back = findViewById(R.id.back_buttonroute);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //draw polylines one map
        Polyline polyline = googleMap.addPolyline(new PolylineOptions().width(4).color(Color.BLACK));
        if(postions.size() > 2){
            polyline.setPoints(postions);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(getCenterOfPolygon(postions), 15));
        }
    }

    private static LatLng getCenterOfPolygon(List<LatLng> latLngList) {
        //calculate the center point of the polyline
        double[] centroid = {0.0, 0.0};
        for (int i = 0; i < latLngList.size(); i++) {
            centroid[0] += latLngList.get(i).latitude;
            centroid[1] += latLngList.get(i).longitude;
        }
        int totalPoints = latLngList.size();
        return new LatLng(centroid[0] / totalPoints, centroid[1] / totalPoints);
    }
}
