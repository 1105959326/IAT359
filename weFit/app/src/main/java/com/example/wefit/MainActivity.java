package com.example.wefit;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //initiliza the ImageButton
    private ImageButton recordButton, inforButton, gpsButton, tempButton;

    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout layout=(LinearLayout)findViewById(R.id.button_activity);
        layout.setVisibility(View.VISIBLE);

        findButton();

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();


        ft.replace(R.id.activity_window, new RecordingActivity());
        ft.commitAllowingStateLoss();


        int id = getIntent().getIntExtra("name", 0);
        if (id == 1) {
            LinearLayout layout1=(LinearLayout)findViewById(R.id.button_activity);
            layout1.setVisibility(View.INVISIBLE);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_window,new LoginFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void findButton(){
        //find all view by id
        recordButton =(ImageButton) findViewById(R.id.record_button);
        inforButton = (ImageButton) findViewById(R.id.infor_button);
        //gpsButton =(ImageButton) findViewById(R.id.gps_button);
        tempButton = (ImageButton)findViewById(R.id.temp_button);

        recordButton.setOnClickListener(this);
        inforButton.setOnClickListener(this);
        //gpsButton.setOnClickListener(this);
        tempButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        //change the activity of fragment based on the button that user click
        switch (v.getId()){
            case R.id.record_button:
                Log.d("debug", "123");
                ft.replace(R.id.activity_window,new RecordingActivity());

                break;
            case R.id.infor_button:
                ft.replace(R.id.activity_window,new SecondFragment());
                break;
//            case R.id.gps_button:
//                ft.replace(R.id.activity_window,new LoginFragment());
//                break;
            case R.id.temp_button:
                ft.replace(R.id.activity_window, new TempuratureFragment());
                break;
            default:
                break;
        }

        ft.commit();
    }



}