package com.example.wefit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class RecordingActivity extends Fragment implements View.OnClickListener{

    private Button runBtn, rideBtn, startBtn;

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


    }

    @Override
    public void onClick(View v) {

    }
}