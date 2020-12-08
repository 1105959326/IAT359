package com.example.wefit;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RecordPage extends Activity implements View.OnClickListener, LocationListener {

    private Button startBtn, resumeBtn, finishBtn;
    private Button mapButton;
    private boolean state = false;
    private TextView  typeText, stateText, timeText, distanceText, speedText, caloryText;
    private String type;
    private int cnt = 0, burnedCal;
    private CountDownTimer t;
    private Location originL, currentL;
    private LocationManager locationManager;
    private RecordedDatabase db;
    private double speed = 0;
    private List<LatLng> points = new ArrayList<LatLng>();
    private float final_distance = (float) 0.000;

    public void onCreate(Bundle b) {
        //create the layout
        super.onCreate(b);
        setContentView(R.layout.record_page);

        findById();
        //get implicit information
        type = getIntent().getStringExtra("state");
        burnedCal = getIntent().getIntExtra("burnedCal", 0);
        //set timer
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
        //get location service
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, this);


        activity();
    }

    protected void findById(){
        //find all view by id
        startBtn = findViewById(R.id.start_btn);
        resumeBtn = findViewById(R.id.resume_button);
        finishBtn = findViewById(R.id.finish_btn);
        timeText = findViewById(R.id.time_text);
        mapButton = findViewById(R.id.openMap);

        typeText = findViewById(R.id.activity_type);
        stateText = findViewById(R.id.activity_state);
        distanceText = findViewById(R.id.distance_txt);
        speedText = findViewById(R.id.speed_text);
        caloryText = findViewById(R.id.speed2);

        startBtn.setOnClickListener(this);
        resumeBtn.setOnClickListener(this);
        finishBtn.setOnClickListener(this);
        mapButton.setOnClickListener(this);

        resumeBtn.setVisibility(View.INVISIBLE);
        finishBtn.setVisibility(View.INVISIBLE);
    }

    private void activity(){
        stateText.setText("");
        typeText.setText(type);
        //call the database
        db = new RecordedDatabase(this);
    }

    @Override
    public void onClick(View v) {
        //change the state based on the button that user clicked
        if (v.getId() == R.id.start_btn){
            startBtn.setVisibility(View.GONE);
            resumeBtn.setVisibility(View.VISIBLE);
            finishBtn.setVisibility(View.VISIBLE);
            state = true;
            t.start();
            stateText.setText("Started");
        }
        //start and pause timer
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
            //finish this activity
            t.cancel();
            if(cnt > 0)recordData();
            finish();
        }
        //start a the new activity, and sent data into the activity
        if (v.getId() == R.id.openMap){
            Intent i = new Intent(this, MapRecordPage.class);
            i.putExtra("time", cnt);
            i.putExtra("speed", speed);
            i.putExtra("dist", final_distance);
            i.putParcelableArrayListExtra("points", (ArrayList<? extends Parcelable>) points);
            //t.cancel();
            this.startActivity(i);
        }
    }


    @Override
    protected void onResume() {

        super.onResume();
    }

    protected void onStop() {

        super.onStop();

    }



    private void recordData() {
        //record data into the table
        ArrayList<String> stringlist = new ArrayList<String>();
        String s = "";
        for (int i = 0; i < points.size();i++){
            stringlist.add(String.valueOf(points.get(i).latitude));
            stringlist.add(String.valueOf(points.get(i).longitude));
            s += String.valueOf(points.get(i).latitude) + ", "+ String.valueOf(points.get(i).longitude) + ",";
        }

        long id = db.insertData(typeText.getText().toString(), distanceText.getText().toString(),
                Integer.toString(cnt), speedText.getText().toString(), caloryText.getText().toString(), s);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        //when location change, add the location to an arraylist, and update data to UI
        points.add(new LatLng(location.getLatitude(), location.getLongitude()));
        if (originL == null) originL = location;
        else{
            currentL = location;
            float distance = currentL.distanceTo(originL);
            originL = currentL;
            final_distance += distance;
            speed = final_distance/cnt*60;
            distanceText.setText(String.format("%.3f", final_distance/1000));
            speedText.setText(String.format("%.2f", speed));
            caloryText.setText(String.format("%.2f", final_distance/1000 * burnedCal));
        }
    }

    private double calculate(Location oL, Location fL) {
        //calculate the dsistance that user traveled based two location by calculate the difference between two altitudes and two longitude
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
