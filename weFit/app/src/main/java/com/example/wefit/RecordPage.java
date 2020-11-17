package com.example.wefit;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class RecordPage extends Activity implements View.OnClickListener, SensorEventListener, LocationListener {

    private Button startBtn, resumeBtn, finishBtn;
    private boolean state = false;
    private TextView  typeText, stateText, timeText, distanceText, speedText, caloryText;
    private String type;
    private SensorManager sm;
    private Sensor sensorS;
    private int cnt;
    private CountDownTimer t;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location originL, currentL;
    private LocationManager locationManager;
    private float final_distance = (float) 0.000;


    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.record_page);

        findById();

        sm = (SensorManager)getSystemService(SENSOR_SERVICE); //set sensor manager
        sensorS = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); //get accelerometer


        type = getIntent().getStringExtra("state");

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        t = new CountDownTimer(Long.MAX_VALUE, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                cnt++;
                long millis = cnt;
                int second = (int)(millis/60);
                int minutes = (int) (second/60);
                second %= 60;
                millis = millis % 60;

                timeText.setText(String.format("%02d:%02d:%02d", minutes, second, millis));
            }

            @Override
            public void onFinish() {

            }
        };

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, this);


        activity();
    }

    protected void findById(){
        startBtn = findViewById(R.id.start_btn);
        resumeBtn = findViewById(R.id.resume_button);
        finishBtn = findViewById(R.id.finish_btn);
        timeText = findViewById(R.id.time_text);

        typeText = findViewById(R.id.activity_type);
        stateText = findViewById(R.id.activity_state);
        distanceText = findViewById(R.id.distance_txt);
        speedText = findViewById(R.id.speed_text);
        caloryText = findViewById(R.id.speed2);

        startBtn.setOnClickListener(this);
        resumeBtn.setOnClickListener(this);
        finishBtn.setOnClickListener(this);

        resumeBtn.setVisibility(View.INVISIBLE);
        finishBtn.setVisibility(View.INVISIBLE);
    }

    private void activity(){
        stateText.setText("");
        typeText.setText(type);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start_btn){
            startBtn.setVisibility(View.GONE);
            resumeBtn.setVisibility(View.VISIBLE);
            finishBtn.setVisibility(View.VISIBLE);
            state = true;
            t.start();
            stateText.setText("Started");
        }

        if(v.getId() == R.id.resume_button){
            if(state) {
                resumeBtn.setText("Resume");
                state = false;
                t.cancel();
                stateText.setText("Paused");
            }
            else{
                resumeBtn.setText("pause");
                state = true;
                t.start();
                stateText.setText("Started");
            }
        }

        if (v.getId() == R.id.finish_btn){
            t.cancel();
            finish();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
//        Sensor sensor = event.sensor;
//        float[] values = event.values;
//        int value = -1;
//
//        if (values.length > 0) {
//            value = (int) values[0];
//        }
//
//
//        if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
//            stepNum++;
//        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {

        super.onResume();

        sm.registerListener(this, sensorS, SensorManager.SENSOR_DELAY_FASTEST);
    }

    protected void onStop() {

        super.onStop();

        sm.unregisterListener(this, sensorS);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (originL == null) originL = location;
        else{
            currentL = location;
            //double distance = calculate(originL, currentL);
            float distance = currentL.distanceTo(originL);
            originL = currentL;
            final_distance += distance;
            distanceText.setText(String.format("%.3f", final_distance/1000));
            speedText.setText(String.format("%.2f", final_distance/cnt*60));
            caloryText.setText(String.format("%.2f", final_distance/1000 * 0.625));
        }
    }

    private double calculate(Location oL, Location fL) {
        double latA = Math.toRadians(oL.getAltitude());
        double lonA = Math.toRadians(oL.getLongitude());
        double latB = Math.toRadians(fL.getAltitude());
        double lonB = Math.toRadians(fL.getLongitude());
        double cosAng = (Math.cos(latA) * Math.cos(latB) * Math.cos(lonB-lonA)) +
                (Math.sin(latA) * Math.sin(latB));
        double ang = Math.acos(cosAng);
        double dist = ang *6371;
        return dist;
    }
}
