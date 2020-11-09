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
        firstButton = view.findViewById(R.id.button_first);
        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(secondFragment==null){
                       secondFragment = new SecondFragment();
                    }
                    getFragmentManager().beginTransaction().replace(R.id.activity_window,secondFragment).commitAllowingStateLoss();

                }
        });

<<<<<<< HEAD:weFit/app/src/main/java/com/example/wefit/RecordingActivity.java

    }

    @Override
    public void onClick(View v) {

=======
>>>>>>> 0eaaf152f5ee77a18b71926ecf993f20d8e1e069:weFit/app/src/main/java/com/example/wefit/FirstFragment.java
    }
}