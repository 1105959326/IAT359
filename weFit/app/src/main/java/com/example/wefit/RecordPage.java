package com.example.wefit;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RecordPage extends Activity implements View.OnClickListener, SensorEventListener {

    private Button startBtn, resumeBtn, finishBtn;
    private boolean state = false;
    private TextView stepText, typeText, stateText;
    private String type;
    private SensorManager sm;
    private Sensor sensorS;
    private int stepNum = 0;


    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.record_page);

        findById();

        type = getIntent().getStringExtra("state");
        if(type == "run")findViewById(R.id.step_layer).setVisibility(View.VISIBLE);

        sm = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        sensorS = sm.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        activity();
    }

    private void findById(){
        startBtn = findViewById(R.id.start_btn);
        resumeBtn = findViewById(R.id.resume_button);
        finishBtn = findViewById(R.id.finish_btn);

        stepText = findViewById(R.id.steps);
        typeText = findViewById(R.id.activity_type);
        stateText = findViewById(R.id.activity_state);


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
            stateText.setText("Started");
        }

        if(v.getId() == R.id.resume_button){
            if(state) {
                resumeBtn.setText("Resume");
                state = false;
                stateText.setText("Paused");
            }
            else{
                resumeBtn.setText("pause");
                state = true;
                stateText.setText("Started");
            }
        }

        if (v.getId() == R.id.finish_btn){
            finish();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        stepNum = (int) event.values[0];


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {

        super.onResume();

        sm.registerListener(this, sensorS, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onStop() {

        super.onStop();

        sm.unregisterListener(this, sensorS);
    }
}
