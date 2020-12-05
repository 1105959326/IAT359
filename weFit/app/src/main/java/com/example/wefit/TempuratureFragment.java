package com.example.wefit;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.internal.GetServiceRequest;
import com.google.android.gms.location.LocationServices;

import static android.content.Context.SENSOR_SERVICE;

public class TempuratureFragment extends Fragment implements SensorEventListener {
    private SensorManager sm;
    private Sensor sensor;
    private ImageView image;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tempurature, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sm = (SensorManager)getContext().getSystemService(SENSOR_SERVICE); //set sensor manager
        sensor = sm.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE); //get accelerometer
        sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        image = (ImageView)view.findViewById(R.id.temp_img);
        image.setImageResource(R.drawable.state1);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values[0] > 10)image.setImageResource(R.drawable.state1);
        else if (event.values[0] > 2)image.setImageResource(R.drawable.state2);
        else if (event.values[0] > -6)image.setImageResource(R.drawable.state3);
        else image.setImageResource(R.drawable.state4);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
