package com.example.wefit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class RecordingActivity extends Fragment implements View.OnClickListener{

    private Button runBtn, rideBtn, startBtn;
    private int state = 1;
    private Button firstButton;
    private SecondFragment secondFragment;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_record, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findId(view);

    }

    private void findId(View view){
        runBtn = view.findViewById(R.id.run_button);
        rideBtn = view.findViewById(R.id.ride_button);
        startBtn = view.findViewById(R.id.startRec);

        runBtn.setOnClickListener(this);
        rideBtn.setOnClickListener(this);
        startBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.run_button){
            state = 1;
        }
        if(v.getId() == R.id.ride_button){
            state = 2;
        }

        if(v.getId() == R.id.startRec){
            if (state == 1){

                Intent i = new Intent(getContext(), RecordPage.class);
                i.putExtra("state", "RUN");
                i.putExtra("burnedCal", 62);
                getContext().startActivity(i);

            }if (state == 2){
                Intent i = new Intent(getContext(), RecordPage.class);
                i.putExtra("state", "RIDE");
                i.putExtra("burnedCal", 29);
                getContext().startActivity(i);

            }
        }

    }
}