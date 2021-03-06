package com.example.wefit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
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

import java.util.ArrayList;
import java.util.List;

public class MapRecordPage extends FragmentActivity implements OnMapReadyCallback, LocationListener {
    Button backBtn;
    TextView textTime, textDist, textSpeed;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    GoogleMap googleMa;
    private static final int REQUEST_CODE = 101;
    private LatLng lastpos;
    private Polyline polyline;
    private int cnt = 0;
    private double speed;
    private CountDownTimer t;
    private List<LatLng> points = new ArrayList<LatLng>();
    private float final_distance = (float) 0.000;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //create the layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_recordpage);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();
        findById();
        //get location service
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 1, this);
        //get implicit data
        cnt = getIntent().getIntExtra("time", 0);
        points = getIntent().getParcelableArrayListExtra("points");

        //set the timer to count time
        t = new CountDownTimer(Long.MAX_VALUE, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                cnt++;
                long millis = cnt;
                int second = (int)(millis/60);
                int minutes = (int) (second/60);
                second %= 60;
                millis = millis % 60;

                textTime.setText(String.format("%02d:%02d:%02d", minutes, second, millis));


            }

            @Override
            public void onFinish() {

            }
        };
        t.start();
    }

    private void findById(){
        //find all view by id
        backBtn = findViewById(R.id.back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                polyline.remove();
                finish();
            }
        });

        textTime = findViewById(R.id.mapTime);


    }

    private void fetchLocation() {
        //check the permission
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        //get user location
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activityMap);
                    assert supportMapFragment != null;
                    supportMapFragment.getMapAsync(MapRecordPage.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //when the map is ready, show user position on the map
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        //set the polyline style on the map
        polyline = googleMap.addPolyline(new PolylineOptions().width(4).color(Color.BLACK));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMa = googleMap;
    }

    @Override
    public void onLocationChanged(Location location){
        //when the location update, draw the polyline on map, and move the camera with user
        if(googleMa != null) {
            lastpos = new LatLng(location.getLatitude(), location.getLongitude());
            googleMa.moveCamera(CameraUpdateFactory.newLatLngZoom(lastpos, 18));
            points.add(lastpos);
            polyline.setPoints(points);
        }
    }


}