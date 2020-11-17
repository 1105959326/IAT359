package com.example.wefit;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    private Button recordButton, inforButton, gpsButton;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private SupportMapFragment supportMapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        findButton();
        setSupportActionBar(toolbar);

        fm = getSupportFragmentManager();

        //supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.myMap);
        ft = fm.beginTransaction();


        ft.replace(R.id.activity_window, new RecordingActivity());
        ft.commitAllowingStateLoss();

        //FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }

    private void findButton(){
        recordButton =(Button) findViewById(R.id.record_button);
        inforButton = (Button) findViewById(R.id.infor_button);
        gpsButton =(Button) findViewById(R.id.gps_button);

        recordButton.setOnClickListener(this);
        inforButton.setOnClickListener(this);
        gpsButton.setOnClickListener(this);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onClick(View v) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        switch (v.getId()){
            case R.id.record_button:
                Log.d("debug", "123");
                ft.replace(R.id.activity_window,new RecordingActivity());

                break;
            case R.id.infor_button:
                ft.replace(R.id.activity_window,new SecondFragment());
                break;
            default:
                break;
        }

        ft.commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}