package com.example.wefit;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class RecordPage extends Activity implements View.OnClickListener, SensorEventListener {

    private Button startBtn, resumeBtn, finishBtn;
    private boolean state = false;
    private TextView stepText, typeText, stateText, timeText;
    private String type;
    private SensorManager sm;
    private Sensor sensorS;
    private int stepNum = 0, cnt;
    private CountDownTimer t;

    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.record_page);

        findById();

        type = getIntent().getStringExtra("state");
        if(type == "RUN")findViewById(R.id.step_layer).setVisibility(View.VISIBLE);

        sm = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        sensorS = sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

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
        activity();
    }

    protected void findById(){
        startBtn = findViewById(R.id.start_btn);
        resumeBtn = findViewById(R.id.resume_button);
        finishBtn = findViewById(R.id.finish_btn);
        timeText = findViewById(R.id.time_text);

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

        stepText.setText(111);
        Log.d("debug", String.valueOf(1));
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
}
